package ru.liga.service;

import ru.liga.dto.DeliveryDto;
import ru.liga.model.Courier;

import java.util.List;

public interface CourierService {
    List<Courier> findAllCouriers();

    Courier findCourierById(Long id);

    DeliveryDto takeOrder(Long courierId, Long orderId);

    void completeDelivery(Long orderId);
}
