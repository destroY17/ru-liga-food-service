package ru.liga.service;

import ru.liga.dto.NewMenuItemDto;
import ru.liga.model.RestaurantMenuItem;

import java.math.BigDecimal;
import java.util.List;

public interface MenuItemService {
    RestaurantMenuItem addMenuItem(NewMenuItemDto menuItem);

    RestaurantMenuItem findMenuItemById(Long id);

    RestaurantMenuItem changePrice(RestaurantMenuItem menuItemById, BigDecimal price);

    List<RestaurantMenuItem> findMenuItemsLessThanPrice(Long restaurantId, BigDecimal price);
}
