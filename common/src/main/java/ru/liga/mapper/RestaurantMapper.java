package ru.liga.mapper;

import org.springframework.stereotype.Component;
import ru.liga.dto.RestaurantDto;
import ru.liga.model.Restaurant;

@Component
public class RestaurantMapper {
    public RestaurantDto mapToDto(Restaurant entity) {
        return new RestaurantDto(entity.getName(), entity.getAddress());
    }
}
