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

/**
 * Контроллер взаимодействия с товарами меню
 */
@RestController
@RequestMapping("kitchen-service/menu-items")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API для работы с товарами меню")
public class MenuItemController {
    private final MenuItemService menuItemService;

    /**
     * Найти товары меню дешевле, чем указанная цена
     *
     * @param restaurantId идентификатор заказа
     * @param price цена, ниже которой будет стоимость найденных товаров
     * @return список товаров меню
     */
    @Operation(summary = "Найти все дешевле заданной цены")
    @GetMapping("/filters/price/less")
    public List<RestaurantMenuItem> findAllCheaperPrice(@RequestParam Long restaurantId,
                                                        @RequestParam @Positive BigDecimal price) {
        log.info("Received GET request to find menu items price<{} in restaurant id={}", price, restaurantId);
        return menuItemService.findAllCheaperPrice(restaurantId, price);
    }

    /**
     * Добавить новый товар в меню ресторана
     *
     * @param menuItem сведения о новом товаре
     * @return полная информация о новом товаре в меню
     */
    @Operation(summary = "Добавить")
    @PostMapping
    public RestaurantMenuItem addMenuItem(@Valid @RequestBody NewMenuItemDto menuItem) {
        log.info("Received POST request to add new menu item:{}", menuItem);
        return menuItemService.addMenuItem(menuItem);
    }

    /**
     * Изменение цены товара меню
     *
     * @param id идентификатор товара
     * @param price новая цена для товара
     * @return товар меню с измененной ценой
     */
    @Operation(summary = "Изменить цену")
    @PatchMapping("/change-price/{id}")
    public RestaurantMenuItem changePrice(@PathVariable Long id,
                                          @RequestParam @Positive BigDecimal price) {
        log.info("Received PATCH request to change price from menu item with id={} to price={}", id, price);
        return menuItemService.changePrice(id, price);
    }
}
