package ru.liga.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.client.OrderFeign;
import ru.liga.dto.DeliveryDto;
import ru.liga.dto.OrderInfoDto;
import ru.liga.mapper.DeliveryToOrderMapper;
import ru.liga.model.OrderStatus;

import ru.liga.service.DeliveryService;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final OrderFeign orderFeign;
    private final DeliveryToOrderMapper deliveryToOrderMapper;

    @Override
    public Page<DeliveryDto> findDeliveriesByStatus(Pageable page, OrderStatus status) {
        Page<OrderInfoDto> ordersByStatus = orderFeign.findOrdersByStatus(status);
        return new PageImpl<>(deliveryToOrderMapper.mapToDelivery(ordersByStatus.getContent()));
    }

    public List<OrderInfoDto> findAllOrdersByStatus(OrderStatus status) {
        return orderFeign.findOrdersByStatus(status).getContent();
    }
}
