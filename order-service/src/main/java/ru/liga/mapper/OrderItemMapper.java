package ru.liga.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.dto.OrderItemDto;
import ru.liga.model.OrderItem;
import ru.liga.model.RestaurantMenuItem;
import ru.liga.repository.MenuItemRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemMapper {
    private final MenuItemRepository menuItemRepository;

    public OrderItem toDto(OrderItemDto dto) {
        RestaurantMenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId()).orElseThrow();

        return OrderItem.builder()
                .restaurantMenuItem(menuItem)
                .quantity(dto.getQuantity())
                .price(menuItem.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())))
                .build();
    }

    public List<OrderItem> toDto(List<OrderItemDto> menuItems) {
        return menuItems.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

















