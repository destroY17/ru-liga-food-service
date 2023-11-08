package ru.liga.service;

import ru.liga.dto.OrderActionDto;

public interface RabbitService {
    void sendDeliveryStatus(OrderActionDto orderAction, String deliveryAction);
    void sendDeliveryInfo(Long orderId, String deliveryAction);
}
