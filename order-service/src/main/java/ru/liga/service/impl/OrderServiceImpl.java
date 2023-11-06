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
import ru.liga.repository.CustomerRepository;
import ru.liga.repository.MenuItemRepository;
import ru.liga.repository.OrderRepository;
import ru.liga.repository.RestaurantRepository;
import ru.liga.service.OrderService;
import ru.liga.util.OrderUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final RabbitServiceImpl rabbitServiceImpl;

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    private final OrderInfoMapper orderInfoMapper;
    private final DeliveryOrderMapper deliveryOrderMapper;

    @Override
    public Page<OrderInfoDto> findAllOrders(Pageable pageable) {
        Page<Order> allOrders = orderRepository.findAll(pageable);
        return new PageImpl<>(orderInfoMapper.toDto(allOrders.getContent()),
                pageable, allOrders.getTotalElements());
    }

    @Override
    public Page<OrderInfoDto> findOrdersByStatus(Pageable pageable, OrderStatus status) {
        Page<Order> ordersByStatus = orderRepository.findOrdersByStatus(pageable, status);
        return new PageImpl<>(orderInfoMapper.toDto(ordersByStatus.getContent()),
                pageable, ordersByStatus.getTotalElements());
    }

    @Override
    public OrderInfoDto findOrderById(Long id) {
        Order order = getOrderById(id);
        return orderInfoMapper.toDto(order);
    }

    private Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Order id=%d is not found", id)));
    }

    @Override
    @Transactional
    public void updateOrderStatus(OrderActionDto orderAction) {
        orderRepository.updateOrderByStatus(orderAction.getOrderId(), orderAction.getStatus());
    }

    @Override
    public void refundOfFunds(Long orderId) {
        Order order = getOrderById(orderId);
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), OrderStatus.DELIVERY_DENIED);

        //Some logic for refund
        Customer customer = order.getCustomer();
        log.info("Money for order id={} was return to customer id={}", orderId, customer.getId());
    }

    //TODO: refactor method
    @Override
    @Transactional
    public DeliveryOrderDto addOrder(Long customerId, NewOrderDto newOrder) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new DataNotFoundException(String.format("Customer id=%d not found", customerId)));
        Restaurant restaurant = restaurantRepository.findById(newOrder.getRestaurantId())
                .orElseThrow(() ->
                        new DataNotFoundException(String.format("Restaurant id=%d not found",
                                newOrder.getRestaurantId())));

        List<OrderItemDto> menuItems = newOrder.getMenuItems();
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDto menuItem : menuItems) {
            RestaurantMenuItem restaurantMenuItem =
                    menuItemRepository.findById(menuItem.getMenuItemId())
                            .orElseThrow(() ->
                                    new DataNotFoundException(String.format("Menu item id=%d not found",
                                            menuItem.getMenuItemId())));

            orderItems.add(OrderItem.builder()
                    .restaurantMenuItem(restaurantMenuItem)
                    .quantity(menuItem.getQuantity())
                    .price(restaurantMenuItem.getPrice().multiply(BigDecimal.valueOf(menuItem.getQuantity())))
                    .build()
            );
        }

        Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .status(OrderStatus.CUSTOMER_CREATED)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .orderItems(orderItems)
                .build();

        return deliveryOrderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void payForOrder(Long orderId, String paymentUrl) {
        Order order = getOrderById(orderId);
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), OrderStatus.CUSTOMER_CREATED);
        orderRepository.updateOrderByStatus(orderId, OrderStatus.CUSTOMER_PAID);
        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.CUSTOMER_PAID);
        rabbitServiceImpl.sendNewOrder(orderAction, "newOrderToNotification");
    }
}
