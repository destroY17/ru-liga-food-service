package ru.liga.deliveryservice.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.dto.DeliveryDto;
import ru.liga.deliveryservice.dto.OrderActionDto;
import ru.liga.deliveryservice.model.DeliveryStatus;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    @Override
    public List<DeliveryDto> findAllDeliveries(Pageable page, DeliveryStatus status) {
        return null;
    }

    @Override
    public void addDelivery(Long id, OrderActionDto orderAction) {
    }
}
