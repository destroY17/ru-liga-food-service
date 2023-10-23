package ru.liga.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.batismapper.CourierMapper;
import ru.liga.client.OrderFeign;
import ru.liga.dto.DeliveryDto;
import ru.liga.dto.OrderInfoDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.mapper.DeliveryToOrderMapper;
import ru.liga.model.Courier;
import ru.liga.model.OrderStatus;

import ru.liga.service.DeliveryService;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final OrderFeign orderFeign;
    private final DeliveryToOrderMapper deliveryToOrderMapper;
    private final CourierMapper courierMapper;

    @Override
    public Page<DeliveryDto> findDeliveriesByStatus(Pageable pageable, OrderStatus status) {
        Page<OrderInfoDto> ordersByStatus = orderFeign.findOrdersByStatus(pageable, status);
        return new PageImpl<>(deliveryToOrderMapper.mapToDelivery(ordersByStatus.getContent()));
    }

    public List<Courier> findAllCouriers() {
        return courierMapper.findAll();
    }

    public Courier findCourierById(Long id) {
        return courierMapper.findCourierById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Courier with id = " + id + " is not found"));
    }
}
