package ru.liga.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.dto.OrderInfoDto;
import ru.liga.model.Order;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderInfoMapper {
    private final MenuItemInOrderMapper menuItemInOrderMapper;
    private final RestaurantMapper restaurantMapper;
    private final CustomerMapper customerMapper;

    public OrderInfoDto mapToDto(Order entity) {
        return new OrderInfoDto(
                entity.getId(),
                customerMapper.mapToDto(entity.getCustomer()),
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
