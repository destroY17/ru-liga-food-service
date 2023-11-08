package ru.liga.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.dto.OrderInfo;
import ru.liga.model.Order;

import java.util.List;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class OrderInfoMapper {
    @Autowired
    protected MenuItemInOrderMapper menuItemInOrderMapper;
    @Autowired
    protected RestaurantMapper restaurantMapper;

    @Mapping(target = "orderId", source = "entity.id")
    @Mapping(target = "restaurant", expression = "java(restaurantMapper.toDto(entity.getRestaurant()))")
    @Mapping(target = "timestamp", source = "entity.timestamp")
    @Mapping(target = "items", expression = "java(menuItemInOrderMapper.toDto(entity.getOrderItems()))")
    public abstract OrderInfo toDto(Order entity);

    public abstract List<OrderInfo> toDto(List<Order> entities);
}
