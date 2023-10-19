package ru.liga.orderservice.service;

import ru.liga.orderservice.dto.NewOrderItemDto;
import ru.liga.orderservice.model.OrderItem;

public interface OrderItemsService {
    OrderItem addOrderItem(NewOrderItemDto newOrderItemDto);

    void deleteOrderItemById(Long id);
}
