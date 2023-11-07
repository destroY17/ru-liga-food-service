package ru.liga.service;

import ru.liga.dto.OrderActionDto;

public interface RabbitService {
    void sendNewOrder(OrderActionDto orderAction, String routingKey);
}
