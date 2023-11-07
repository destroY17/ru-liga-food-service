package ru.liga.service;

public interface KitchenService {
    void acceptOrder(Long orderId);

    void completeOrder(Long orderId, String routingKey);

    void denyOrder(Long orderId);

}
