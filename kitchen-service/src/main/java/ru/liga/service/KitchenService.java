package ru.liga.service;

import java.util.UUID;

public interface KitchenService {
    void acceptOrder(UUID orderId);

    void completeOrder(UUID orderId, String routingKey);

    void denyOrder(UUID orderId);

}
