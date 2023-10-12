package ru.liga.orderservice.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.DeliveryOrderDto;
import ru.liga.orderservice.dto.NewOrderDto;
import ru.liga.orderservice.dto.OrderDto;
import ru.liga.orderservice.model.OrderStatus;
import ru.liga.orderservice.service.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public List<OrderDto> findAllOrders(Pageable page, OrderStatus status) {
        return null;
    }

    @Override
    public OrderDto findOrderById(Long id) {
        return null;
    }

    @Override
    public DeliveryOrderDto addOrder(NewOrderDto newOrder) {
        return null;
    }
}
