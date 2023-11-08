package ru.liga.service;

import ru.liga.dto.OrderActionDto;

import java.util.UUID;

public interface RabbitService {
    void sendDeliveryStatus(OrderActionDto orderAction, String deliveryAction);
    void sendDeliveryInfo(UUID orderId, String deliveryAction);
}
