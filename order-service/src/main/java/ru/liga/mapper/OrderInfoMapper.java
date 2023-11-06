package ru.liga.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.dto.OrderInfoDto;
import ru.liga.model.Order;

import java.util.List;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class OrderInfoMapper {
    @Autowired
    protected MenuItemInOrderMapper menuItemInOrderMapper;
    @Autowired
    protected RestaurantMapper restaurantMapper;
    @Autowired
    protected CustomerMapper customerMapper;

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "customer", expression = "java(customerMapper.toDto(entity.getCustomer()))")
    @Mapping(target = "restaurant", expression = "java(restaurantMapper.toDto(entity.getRestaurant()))")
    @Mapping(target = "timestamp", source = "entity.timestamp")
    @Mapping(target = "items", expression = "java(menuItemInOrderMapper.toDto(entity.getOrderItems()))")
    public abstract OrderInfoDto toDto(Order entity);

    public abstract List<OrderInfoDto> toDto(List<Order> entities);
}
