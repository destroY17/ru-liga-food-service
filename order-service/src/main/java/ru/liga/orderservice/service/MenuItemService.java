package ru.liga.orderservice.service;

import ru.liga.orderservice.dto.NewMenuItemDto;
import ru.liga.orderservice.model.RestaurantMenuItem;

import java.math.BigDecimal;

public interface MenuItemService {
    RestaurantMenuItem addMenuItem(NewMenuItemDto menuItem);

    RestaurantMenuItem findMenuItemById(Long id);

    RestaurantMenuItem changePrice(RestaurantMenuItem menuItemById, BigDecimal price);
}
