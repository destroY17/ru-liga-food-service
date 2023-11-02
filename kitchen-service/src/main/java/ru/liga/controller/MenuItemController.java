package ru.liga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.NewMenuItemDto;
import ru.liga.model.RestaurantMenuItem;
import ru.liga.service.MenuItemService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API для работы с товарами меню")
public class MenuItemController {
    private final MenuItemService menuItemService;

    @Operation(summary = "Найти все дешевле заданной цены")
    @GetMapping("/{restaurantId}")
    public List<RestaurantMenuItem> findAllCheaperPrice(@PathVariable Long restaurantId,
                                                        @RequestParam @Positive BigDecimal price) {
        log.info("Received GET request to find menu items price<{} in restaurant id={}", price, restaurantId);
        return menuItemService.findAllCheaperPrice(restaurantId, price);
    }

    @Operation(summary = "Добавить")
    @PostMapping
    public RestaurantMenuItem addMenuItem(@Valid @RequestBody NewMenuItemDto menuItem) {
        log.info("Received POST request to add new menu item:{}", menuItem);
        return menuItemService.addMenuItem(menuItem);
    }

    @Operation(summary = "Изменить цену")
    @PatchMapping("/{id}/change-price")
    public RestaurantMenuItem changePrice(@PathVariable Long id,
                                          @RequestParam @Positive BigDecimal price) {
        log.info("Received PATCH request to change price from menu item with id={} to price={}", id, price);
        return menuItemService.changePrice(menuItemService.findMenuItemById(id), price);
    }
}
