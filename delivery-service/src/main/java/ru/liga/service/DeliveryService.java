package ru.liga.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.liga.dto.DeliveryDto;
import ru.liga.model.Courier;
import ru.liga.model.OrderStatus;

import java.util.List;

public interface DeliveryService {
    Page<DeliveryDto> findDeliveriesByStatus(Pageable page, OrderStatus status);
    List<Courier> findAllCouriers();
}
