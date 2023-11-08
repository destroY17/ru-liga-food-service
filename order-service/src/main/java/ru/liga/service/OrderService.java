package ru.liga.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.liga.dto.*;
import ru.liga.model.OrderStatus;

public interface OrderService {
    Page<OrderInfo> findAllOrders(Pageable page);

    Page<OrderInfo> findOrdersByStatus(Pageable pageable, OrderStatus status);

    OrderInfo findOrderById(Long id);

    DeliveryOrderDto addOrder(Long customerId, NewOrderDto newOrder);

    void payForOrder(Long orderId, String paymentUrl);

    void updateOrderStatus(OrderActionDto orderAction);

    void refundOfFunds(Long orderId);
}
