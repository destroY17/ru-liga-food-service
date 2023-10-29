package ru.liga.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.liga.dto.DeliveryOrderDto;
import ru.liga.dto.NewOrderDto;
import ru.liga.dto.OrderInfoDto;
import ru.liga.model.OrderStatus;

public interface OrderService {
    Page<OrderInfoDto> findAllOrders(Pageable page);

    Page<OrderInfoDto> findOrdersByStatus(Pageable pageable, OrderStatus status);

    OrderInfoDto findOrderById(Long id);

    DeliveryOrderDto addOrder(Long customerId, NewOrderDto newOrder);

    void sendNewOrder(Long orderId, String routingKey);

    void payForOrder(Long orderId, String paymentUrl);
}
