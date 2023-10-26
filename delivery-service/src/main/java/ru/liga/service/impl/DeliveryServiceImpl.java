package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.batismapper.CourierMapper;
import ru.liga.dto.DeliveryDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.mapper.DeliveryToOrderMapper;
import ru.liga.model.Courier;

import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import ru.liga.service.DeliveryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryToOrderMapper deliveryToOrderMapper;
    private final OrderRepository orderRepository;
    private final CourierMapper courierMapper;

    @Override
    public Page<DeliveryDto> findAvailableDeliveries(Pageable pageable) {
        Page<Order> deliveries = orderRepository.findOrdersByStatus(pageable, OrderStatus.DELIVERY_PENDING);

        return new PageImpl<>(deliveryToOrderMapper.toDto(deliveries.getContent()),
                pageable, deliveries.getTotalElements());
    }

    @Override
    public List<Courier> findAllCouriers() {
        return courierMapper.findAll();
    }

    @Override
    public Courier findCourierById(Long id) {
        return courierMapper.findCourierById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Courier with id = " + id + " is not found"));
    }
}
