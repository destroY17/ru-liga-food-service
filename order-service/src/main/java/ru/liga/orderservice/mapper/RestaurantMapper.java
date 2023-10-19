package ru.liga.orderservice.mapper;

import org.springframework.stereotype.Component;
import ru.liga.orderservice.dto.RestaurantDto;
import ru.liga.orderservice.model.Restaurant;

@Component
public class RestaurantMapper {
    public RestaurantDto mapToDto(Restaurant entity) {
        return new RestaurantDto(entity.getName());
    }
}
