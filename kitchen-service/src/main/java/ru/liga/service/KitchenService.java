package ru.liga.service;

import ru.liga.dto.OrderActionDto;

public interface KitchenService {
    void acceptOrder(Long orderId);

    void completeOrder(Long orderId, String routingKey);

    void denyOrder(Long orderId);

    void updateOrderStatus(OrderActionDto orderActionDto);
}
