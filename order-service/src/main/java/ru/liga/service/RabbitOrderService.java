package ru.liga.service;


import ru.liga.dto.OrderInfoDto;

public interface RabbitOrderService {
    void sendOrderToDelivery(OrderInfoDto order);
}
