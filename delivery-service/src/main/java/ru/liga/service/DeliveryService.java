package ru.liga.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.liga.dto.DeliveryDto;
import ru.liga.model.OrderStatus;

public interface DeliveryService {
    Page<DeliveryDto> findDeliveriesByStatus(Pageable page, OrderStatus status);
}
