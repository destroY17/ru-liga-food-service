package ru.liga.deliveryservice.service;

import org.springframework.data.domain.Pageable;
import ru.liga.deliveryservice.dto.DeliveryDto;
import ru.liga.deliveryservice.dto.OrderActionDto;
import ru.liga.deliveryservice.model.DeliveryStatus;

import java.util.List;

public interface DeliveryService {
    List<DeliveryDto> findAllDeliveries(Pageable page, DeliveryStatus status);
    void addDelivery(Long id, OrderActionDto orderAction);
}
