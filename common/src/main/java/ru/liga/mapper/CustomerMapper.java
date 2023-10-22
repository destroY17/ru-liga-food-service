package ru.liga.mapper;

import org.springframework.stereotype.Component;
import ru.liga.dto.CustomerDeliveryDto;
import ru.liga.model.Customer;

@Component
public class CustomerMapper {
    //TODO: do something with distance
    public CustomerDeliveryDto mapToDto(Customer entity) {
        return new CustomerDeliveryDto(entity.getAddress(), 1);
    }
}
