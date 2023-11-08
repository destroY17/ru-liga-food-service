package ru.liga.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.liga.dto.*;
import ru.liga.model.OrderStatus;

import java.util.UUID;

public interface OrderService {
    Page<OrderInfo> findAllOrders(Pageable page);

    Page<OrderInfo> findOrdersByStatus(Pageable pageable, OrderStatus status);

    OrderInfo findOrderById(UUID id);

    DeliveryOrderDto addOrder(Long customerId, NewOrderDto newOrder);

    void payForOrder(UUID orderId, String paymentUrl);

    void updateOrderStatus(OrderActionDto orderAction);

    void refundOfFunds(UUID orderId);
}
