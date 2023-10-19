package ru.liga.orderservice.mapper;

import org.springframework.stereotype.Component;
import ru.liga.orderservice.dto.MenuItemInOrderDto;
import ru.liga.orderservice.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuItemInOrderMapper {
    public MenuItemInOrderDto mapToDto(OrderItem entity) {
        return new MenuItemInOrderDto(
                entity.getPrice(),
                entity.getQuantity(),
                entity.getRestaurantMenuItem().getDescription(),
                entity.getRestaurantMenuItem().getImage()
        );
    }

    public List<MenuItemInOrderDto> mapToDto(List<OrderItem> entities) {
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
