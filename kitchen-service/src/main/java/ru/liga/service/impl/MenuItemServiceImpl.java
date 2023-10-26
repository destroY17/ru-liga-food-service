package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.NewMenuItemDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.mapper.NewMenuItemMapper;
import ru.liga.model.RestaurantMenuItem;
import ru.liga.repository.MenuItemRepository;
import ru.liga.service.MenuItemService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final NewMenuItemMapper newMenuItemMapper;

    @Override
    public RestaurantMenuItem addMenuItem(NewMenuItemDto menuItem) {
        return menuItemRepository.save(newMenuItemMapper.toEntity(menuItem));
    }

    @Override
    public RestaurantMenuItem findMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Menu item id=%d is not found", id)));
    }

    @Override
    public RestaurantMenuItem changePrice(RestaurantMenuItem menuItem, BigDecimal price) {
        menuItem.setPrice(price);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public List<RestaurantMenuItem> findAllCheaperPrice(Long restaurantId, BigDecimal price) {
        return menuItemRepository.findMenuItemsLessThanPrice(restaurantId, price);
    }
}
