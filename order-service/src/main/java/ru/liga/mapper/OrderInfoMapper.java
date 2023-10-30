package ru.liga.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.dto.OrderInfoDto;
import ru.liga.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderInfoMapper {
    private final MenuItemInOrderMapper menuItemInOrderMapper;
    private final RestaurantMapper restaurantMapper;
    private final CustomerMapper customerMapper;

    public OrderInfoDto toDto(Order entity) {
        return new OrderInfoDto(
                entity.getId(),
                Optional.ofNullable(entity.getCustomer()).isPresent() ?
                        customerMapper.toDto(entity.getCustomer()) : null,
                restaurantMapper.toDto(entity.getRestaurant()),
                entity.getTimestamp(),
                menuItemInOrderMapper.toDto(entity.getOrderItems())
        );
    }

    public List<OrderInfoDto> toDto(List<Order> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
