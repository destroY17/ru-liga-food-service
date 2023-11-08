package ru.liga.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.liga.dto.DeliveryDto;

public interface DeliveryService {
    Page<DeliveryDto> findAvailableDeliveries(Pageable pageable);

    void pickOrder(Long orderId);
}
