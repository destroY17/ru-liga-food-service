package ru.liga.deliveryservice.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.dto.CustomerDto;
import ru.liga.deliveryservice.dto.DeliveryDto;
import ru.liga.deliveryservice.dto.RestaurantDto;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    @Override
    public List<DeliveryDto> findAllDeliveries(Pageable page) {
        DeliveryDto deliveryDto = new DeliveryDto(
                1L,
                new RestaurantDto("RestName", 111.222),
                new CustomerDto("testAddress", 777.777),
                123.45
        );
        return new ArrayList<>() {{
            add(deliveryDto);
        }};
    }
}
