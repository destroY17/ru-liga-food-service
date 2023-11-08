package ru.liga.service;

import ru.liga.dto.NewMenuItemDto;
import ru.liga.model.RestaurantMenuItem;

import java.math.BigDecimal;
import java.util.List;

public interface MenuItemService {
    RestaurantMenuItem addMenuItem(NewMenuItemDto menuItem);

    RestaurantMenuItem findMenuItemById(Long id);

    List<RestaurantMenuItem> findAllCheaperPrice(Long restaurantId, BigDecimal price);

    RestaurantMenuItem changePrice(Long menuItemId, BigDecimal price);
}
