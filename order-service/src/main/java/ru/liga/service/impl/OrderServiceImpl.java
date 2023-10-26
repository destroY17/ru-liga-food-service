package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.dto.DeliveryOrderDto;
import ru.liga.dto.NewOrderDto;
import ru.liga.dto.OrderInfoDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.mapper.DeliveryOrderMapper;
import ru.liga.mapper.NewOrderMapper;
import ru.liga.mapper.OrderInfoMapper;

import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import ru.liga.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderInfoMapper orderInfoMapper;
    private final NewOrderMapper newOrderMapper;
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
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Order with id = %d  is not found", id)));
        return orderInfoMapper.toDto(order);
    }

    @Override
    public DeliveryOrderDto addOrder(Long customerId, NewOrderDto newOrder) {
        Order order = newOrderMapper.toEntity(customerId, newOrder);
        order.setStatus(OrderStatus.CUSTOMER_CREATED);
        orderRepository.save(order);
        return deliveryOrderMapper.toDto(order);
    }
}
