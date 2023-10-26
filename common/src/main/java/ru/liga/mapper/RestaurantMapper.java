package ru.liga.mapper;

import org.springframework.stereotype.Component;
import ru.liga.dto.RestaurantDto;
import ru.liga.model.Restaurant;

@Component
public class RestaurantMapper {
    public RestaurantDto toDto(Restaurant entity) {
        return new RestaurantDto(entity.getName(), entity.getAddress());
    }
}
