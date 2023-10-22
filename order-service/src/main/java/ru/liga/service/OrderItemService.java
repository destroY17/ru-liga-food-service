package ru.liga.service;

import ru.liga.model.OrderItem;
import ru.liga.dto.NewOrderItemDto;

public interface OrderItemService {
    OrderItem addOrderItem(NewOrderItemDto newOrderItemDto);

    void deleteOrderItemById(Long id);
}
