package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.batismapper.CourierMapper;
import ru.liga.dto.DeliveryDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.mapper.DeliveryToOrderMapper;
import ru.liga.model.Courier;

import ru.liga.model.CourierStatus;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import ru.liga.service.DeliveryService;
import ru.liga.util.OrderUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {
    private final DistanceCalculator distanceCalculator;

    private final OrderRepository orderRepository;

    private final DeliveryToOrderMapper deliveryToOrderMapper;
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
                .orElseThrow(() -> new DataNotFoundException("Courier with id =" + id + " is not found"));
    }

    @Override
    @Transactional
    public void assignCourier(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order id=" + orderId + " is not found"));
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), OrderStatus.DELIVERY_PENDING);

        List<Courier> inactiveCouriers = courierMapper.findByStatus(CourierStatus.INACTIVE);

        if (inactiveCouriers.size() == 0) {
            log.info("Inactive couriers not found");
            return;
        }

        String restaurantAddress = order.getRestaurant().getAddress();
        Courier nearest = findNearestCourier(inactiveCouriers, restaurantAddress);
        courierMapper.updateStatus(nearest.getId(), CourierStatus.ACTIVE);
        order.setCourier(nearest);
        orderRepository.updateOrderByStatus(order.getId(), OrderStatus.DELIVERY_PICKING);

        log.info("Courier id={} is assigned to order id={}", nearest.getId(), order.getId());
    }

    private Courier findNearestCourier(List<Courier> couriers, String restaurantAddress) {
        if (couriers.size() == 0) {
            throw new IllegalArgumentException("couriers must not be empty");
        }

        Courier assigned = couriers.get(0);
        double minDistance =
                distanceCalculator.calculateDistance(restaurantAddress, assigned.getCoordinates());

        for (Courier courier : couriers) {
            double distance =
                    distanceCalculator.calculateDistance(restaurantAddress, courier.getCoordinates());

            if (distance < minDistance) {
                minDistance = distance;
                assigned = courier;
            }
        }
        return assigned;
    }
}
