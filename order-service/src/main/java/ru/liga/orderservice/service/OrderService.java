package ru.liga.orderservice.service;

import org.springframework.data.domain.Pageable;
import ru.liga.orderservice.dto.DeliveryOrderDto;
import ru.liga.orderservice.dto.NewOrderDto;
import ru.liga.orderservice.dto.OrderDto;
import ru.liga.orderservice.model.OrderStatus;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAllOrders(Pageable page, OrderStatus status);
    OrderDto findOrderById(Long id);
    DeliveryOrderDto addOrder(NewOrderDto newOrder);
}
