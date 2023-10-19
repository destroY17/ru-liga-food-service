package ru.liga.orderservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.*;
import ru.liga.orderservice.exception.DataNotFoundException;
import ru.liga.orderservice.mapper.OrderInfoMapper;
import ru.liga.orderservice.model.Order;
import ru.liga.orderservice.repository.OrderRepository;
import ru.liga.orderservice.service.OrderService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderInfoMapper orderInfoMapper;

    @Override
    public Page<OrderInfoDto> findAllOrders(Pageable pageable) {
        Page<Order> allOrders = orderRepository.findAll(pageable);
        return new PageImpl<>(orderInfoMapper.mapToDto(allOrders.getContent()));
    }

    @Override
    public OrderInfoDto findOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order is not found"));
        return orderInfoMapper.mapToDto(order);
    }

    @Override
    public DeliveryOrderDto addOrder(NewOrderDto newOrder) {
        return new DeliveryOrderDto(1L, "supersecreturl", LocalDateTime.now());
    }
}
