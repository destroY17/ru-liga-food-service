package ru.liga.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.liga.dto.DeliveryDto;
import ru.liga.model.Courier;

import java.util.List;

public interface DeliveryService {
    Page<DeliveryDto> findAvailableDeliveries(Pageable pageable);

    List<Courier> findAllCouriers();

    Courier findCourierById(Long id);

    void notifyCouriers(Long orderId);
}
