package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.dto.*;
import ru.liga.exception.DataNotFoundException;
import ru.liga.mapper.DeliveryOrderMapper;
import ru.liga.mapper.OrderInfoMapper;

import ru.liga.model.*;
import ru.liga.repository.*;
import ru.liga.service.OrderService;
import ru.liga.service.RabbitService;
import ru.liga.util.OrderUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final RabbitService rabbitService;
    private final PaymentService paymentService;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    private final OrderInfoMapper orderInfoMapper;
    private final DeliveryOrderMapper deliveryOrderMapper;

    @Override
    public Page<OrderInfo> findAllOrders(Pageable pageable) {
        Page<Order> allOrders = orderRepository.findAll(pageable);
        return new PageImpl<>(orderInfoMapper.toDto(allOrders.getContent()),
                pageable, allOrders.getTotalElements());
    }

    @Override
    public Page<OrderInfo> findOrdersByStatus(Pageable pageable, OrderStatus status) {
        Page<Order> ordersByStatus = orderRepository.findOrdersByStatus(pageable, status);
        return new PageImpl<>(orderInfoMapper.toDto(ordersByStatus.getContent()),
                pageable, ordersByStatus.getTotalElements());
    }

    @Override
    public OrderInfo findOrderById(UUID id) {
        Order order = getOrderById(id);
        return orderInfoMapper.toDto(order);
    }

    private Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order id=" + id+ " not found"));
    }

    @Override
    @Transactional
    public void updateOrderStatus(OrderActionDto orderAction) {
        orderRepository.updateOrderByStatus(orderAction.getOrderId(), orderAction.getStatus());
    }

    @Override
    public void refundOfFunds(UUID orderId) {
        Order order = getOrderById(orderId);
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), OrderStatus.DELIVERY_DENIED);

        paymentService.refundOfFunds(orderId);
    }

    @Override
    @Transactional
    public DeliveryOrderDto addOrder(Long customerId, NewOrderDto newOrder) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Customer id=%d not found",
                        customerId)));

        Restaurant restaurant = restaurantRepository.findById(newOrder.getRestaurantId())
                .orElseThrow(() -> new DataNotFoundException(String.format("Restaurant id=%d not found",
                        newOrder.getRestaurantId())));

        Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .status(OrderStatus.CUSTOMER_CREATED)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        orderRepository.save(order);

        List<OrderItemDto> menuItems = newOrder.getMenuItems();
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDto menuItem : menuItems) {
            RestaurantMenuItem restaurantMenuItem =
                    menuItemRepository.findById(menuItem.getMenuItemId())
                            .orElseThrow(() -> new DataNotFoundException(String.format("Menu item id=%d not found",
                                            menuItem.getMenuItemId())));

            orderItems.add(OrderItem.builder()
                    .order(order)
                    .restaurantMenuItem(restaurantMenuItem)
                    .quantity(menuItem.getQuantity())
                    .price(restaurantMenuItem.getPrice().multiply(BigDecimal.valueOf(menuItem.getQuantity())))
                    .build()
            );
        }
        orderItemRepository.saveAll(orderItems);

        return deliveryOrderMapper.toDto(order);
    }

    @Override
    @Transactional
    public void payForOrder(UUID orderId, String paymentUrl) {
        Order order = getOrderById(orderId);
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), OrderStatus.CUSTOMER_CREATED);

        paymentService.payForOrder(orderId, paymentUrl);
        orderRepository.updateOrderByStatus(orderId, OrderStatus.CUSTOMER_PAID);
        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.CUSTOMER_PAID);

        rabbitService.sendNewOrder(orderAction, "newOrderToNotification");
    }
}
