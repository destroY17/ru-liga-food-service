package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.batismapper.CourierMapper;
import ru.liga.client.DeliveryClient;
import ru.liga.dto.DeliveryDto;
import ru.liga.dto.OrderActionDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.exception.IncorrectCourierStatusException;
import ru.liga.mapper.DeliveryToOrderMapper;
import ru.liga.model.Courier;
import ru.liga.model.CourierStatus;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.queue.DeliveryQueue;
import ru.liga.repository.OrderRepository;
import ru.liga.service.CourierService;
import ru.liga.service.RabbitService;
import ru.liga.util.OrderUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierServiceImpl implements CourierService {
    private final RabbitService rabbitService;
    private final DeliveryClient deliveryClient;

    private final CourierMapper courierMapper;
    private final OrderRepository orderRepository;

    private final DeliveryToOrderMapper deliveryToOrderMapper;

    @Override
    public List<Courier> findAllCouriers() {
        return courierMapper.findAll();
    }

    @Override
    public Courier findCourierById(Long id) {
        return courierMapper.findCourierById(id)
                .orElseThrow(() -> new DataNotFoundException("Courier id=" + id + " not found"));
    }

    @Override
    @Transactional
    public DeliveryDto takeOrder(Long courierId, Long orderId) {
        Courier courier = findCourierById(courierId);
        if (courier.getStatus() == CourierStatus.ACTIVE) {
            throw new IncorrectCourierStatusException("Courier id=" + courierId + " has status=" +
                    courier.getStatus() + "but waiting status=" + CourierStatus.INACTIVE);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order id=" + orderId + " not found"));

        OrderUtil.correctStatusOrElseThrow(order.getStatus(), OrderStatus.DELIVERY_PICKING);

        order.setCourier(courier);

        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.DELIVERY_DELIVERING);

        //deliveryClient.updateOrderStatus(orderAction);
        orderRepository.updateOrderByStatus(orderId, orderAction.getStatus());

        courier.setStatus(CourierStatus.ACTIVE);

        rabbitService.sendDeliveryStatus(orderAction, DeliveryQueue.DELIVERY_TAKEN);

        return deliveryToOrderMapper.toDto(order);
    }

    @Override
    @Transactional
    public void completeDelivery(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order id=" + orderId + " not found"));
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), OrderStatus.DELIVERY_DELIVERING);

        Courier courier = order.getCourier();
        courier.setStatus(CourierStatus.INACTIVE);

        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.DELIVERY_COMPLETE);
        //deliveryClient.updateOrderStatus(orderAction);
        orderRepository.updateOrderByStatus(orderId, orderAction.getStatus());

        rabbitService.sendDeliveryStatus(orderAction, DeliveryQueue.DELIVERY_COMPLETE);
    }
}
