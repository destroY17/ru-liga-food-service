package ru.liga.deliveryservice.service;

import org.springframework.data.domain.Pageable;
import ru.liga.deliveryservice.dto.DeliveryDto;

import java.util.List;

public interface DeliveryService {
    List<DeliveryDto> findAllDeliveries(Pageable page);
}
