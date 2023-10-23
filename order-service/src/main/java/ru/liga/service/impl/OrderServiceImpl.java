package ru.liga.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.dto.DeliveryOrderDto;
import ru.liga.dto.NewOrderDto;
import ru.liga.dto.OrderInfoDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.mapper.OrderInfoMapper;

import ru.liga.model.Courier;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import ru.liga.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public Page<OrderInfoDto> findOrdersByStatusAndCourier(OrderStatus status, Courier courier) {
        List<Order> orders = orderRepository.findOrdersByStatusAndCourier(status, courier);
        return new PageImpl<>(orderInfoMapper.mapToDto(orders));
    }

    @Override
    public Page<OrderInfoDto> findOrdersByStatus(Pageable pageable, OrderStatus status) {
        List<Order> ordersByStatus = orderRepository.findOrdersByStatus(status);
        return new PageImpl<>(orderInfoMapper.mapToDto(ordersByStatus));
    }
}
