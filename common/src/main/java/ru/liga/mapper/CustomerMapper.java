package ru.liga.mapper;

import org.springframework.stereotype.Component;
import ru.liga.dto.CustomerDeliveryDto;
import ru.liga.model.Courier;
import ru.liga.model.Customer;
import ru.liga.util.DistanceCalculator;

@Component
public class CustomerMapper {
    public CustomerDeliveryDto toDto(Customer entity, Courier courier) {
        double distance = DistanceCalculator.calculateDistance(entity.getAddress(), courier.getCoordinates());
        return new CustomerDeliveryDto(entity.getAddress(), distance);
    }
}
