package ru.liga.service;

import ru.liga.dto.DeliveryDto;
import ru.liga.model.Courier;

import java.util.List;
import java.util.UUID;

public interface CourierService {
    List<Courier> findAllCouriers();

    Courier findCourierById(Long id);

    DeliveryDto takeOrder(Long courierId, UUID orderId);

    void completeDelivery(UUID orderId);
}
