package ru.liga.orderservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.NewMenuItemDto;
import ru.liga.orderservice.exception.DataNotFoundException;
import ru.liga.orderservice.mapper.NewMenuItemMapper;
import ru.liga.orderservice.model.RestaurantMenuItem;
import ru.liga.orderservice.repository.MenuItemRepository;
import ru.liga.orderservice.service.MenuItemService;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final NewMenuItemMapper newMenuItemMapper;

    @Override
    public RestaurantMenuItem addMenuItem(NewMenuItemDto menuItem) {
        return menuItemRepository.save(newMenuItemMapper.mapToEntity(menuItem));
    }

    @Override
    public RestaurantMenuItem findMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Menu item is not found"));
    }

    @Override
    public RestaurantMenuItem changePrice(RestaurantMenuItem menuItem, BigDecimal price) {
        menuItem.setPrice(price);
        return menuItemRepository.save(menuItem);
    }
}
