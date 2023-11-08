package ru.liga.mapper;

import org.springframework.stereotype.Component;
import ru.liga.dto.RestaurantDeliveryDto;
import ru.liga.model.Courier;
import ru.liga.model.Restaurant;
import ru.liga.util.DistanceCalculator;

@Component
public class RestaurantDeliveryMapper {
    public RestaurantDeliveryDto toDto(Restaurant entity, Courier courier) {
        double distance = DistanceCalculator.calculateDistance(entity.getAddress(), courier.getCoordinates());
        return new RestaurantDeliveryDto(entity.getAddress(), distance);
    }
}
