package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.dto.DeliveryOrderDto;
import ru.liga.dto.NewOrderDto;
import ru.liga.dto.OrderActionDto;
import ru.liga.dto.OrderInfoDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.mapper.DeliveryOrderMapper;
import ru.liga.mapper.NewOrderMapper;
import ru.liga.mapper.OrderInfoMapper;

import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import ru.liga.service.OrderService;
import ru.liga.util.OrderUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderInfoMapper orderInfoMapper;
    private final NewOrderMapper newOrderMapper;
    private final DeliveryOrderMapper deliveryOrderMapper;
    private final RabbitTemplate rabbitTemplate;

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
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Order with id = %d  is not found", id)));
        return orderInfoMapper.toDto(order);
    }

    @Override
    @Transactional
    public DeliveryOrderDto addOrder(Long customerId, NewOrderDto newOrder) {
        Order order = newOrderMapper.toEntity(customerId, newOrder);
        order.setStatus(OrderStatus.CUSTOMER_CREATED);
        orderRepository.save(order);
        return deliveryOrderMapper.toDto(order);
    }

    @Override
    @Transactional
    public void payForOrder(Long orderId, String paymentUrl) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order id=" + orderId + " is not found"));
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), OrderStatus.CUSTOMER_CREATED);
        orderRepository.updateOrderByStatus(orderId, OrderStatus.CUSTOMER_PAID);
    }

    @Override
    @Transactional
    public void sendNewOrder(Long orderId, String routingKey) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order id=" + orderId + " is not found"));
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), OrderStatus.CUSTOMER_PAID);

        rabbitTemplate.convertAndSend("directExchange", routingKey,
                new OrderActionDto(order.getId(), order.getStatus()));

        log.info("Order id={} send to notification-service", orderId);
    }
}
