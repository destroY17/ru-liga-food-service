package ru.liga.orderservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.orderservice.dto.OrderInfoDto;
import ru.liga.orderservice.model.Order;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderInfoMapper {
    private final MenuItemInOrderMapper menuItemInOrderMapper;
    private final RestaurantMapper restaurantMapper;

    public OrderInfoDto mapToDto(Order entity) {
        return new OrderInfoDto(
                entity.getId(),
                restaurantMapper.mapToDto(entity.getRestaurant()),
                entity.getTimestamp(),
                menuItemInOrderMapper.mapToDto(entity.getOrderItems())
        );
    }

    public List<OrderInfoDto> mapToDto(List<Order> entities) {
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
