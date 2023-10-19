package ru.liga.orderservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.liga.orderservice.dto.DeliveryOrderDto;
import ru.liga.orderservice.dto.NewOrderDto;
import ru.liga.orderservice.dto.OrderInfoDto;

public interface OrderService {
    Page<OrderInfoDto> findAllOrders(Pageable page);
    OrderInfoDto findOrderById(Long id);
    DeliveryOrderDto addOrder(NewOrderDto newOrder);
}
