package ru.liga.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.NewMenuItemDto;
import ru.liga.model.RestaurantMenuItem;
import ru.liga.service.MenuItemService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/restaurant-menu-items")
@AllArgsConstructor
public class MenuItemController {
    private final MenuItemService menuItemService;

    @PostMapping
    public RestaurantMenuItem addMenuItem(@RequestBody NewMenuItemDto menuItem) {
        return menuItemService.addMenuItem(menuItem);
    }

    @PatchMapping("/{id}/change-price")
    public RestaurantMenuItem changeMenuItemPrice(@PathVariable Long id, @RequestParam BigDecimal price) {
        return menuItemService.changePrice(menuItemService.findMenuItemById(id), price);
    }
}
