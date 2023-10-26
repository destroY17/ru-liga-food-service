package ru.liga.mapper;

import org.springframework.stereotype.Component;
import ru.liga.dto.RestaurantDeliveryDto;
import ru.liga.model.Restaurant;

@Component
public class RestaurantDeliveryMapper {
    //TODO:calculate distance
    public RestaurantDeliveryDto toDto(Restaurant entity) {
        return new RestaurantDeliveryDto(entity.getAddress(), 1);
    }
}
