package ru.liga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.dto.MenuItemInOrderDto;
import ru.liga.model.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemInOrderMapper {
    @Mapping(target = "description",
            expression = "java(entity.getRestaurantMenuItem().getDescription())")
    @Mapping(target = "image",
            expression = "java(entity.getRestaurantMenuItem().getImage())")
    MenuItemInOrderDto toDto(OrderItem entity);

    List<MenuItemInOrderDto> toDto(List<OrderItem> entities);
}

