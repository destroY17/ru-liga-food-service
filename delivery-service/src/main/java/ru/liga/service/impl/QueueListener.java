package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.client.DeliveryClient;
import ru.liga.dto.OrderActionDto;
import ru.liga.model.*;
import ru.liga.service.DeliveryService;

@EnableRabbit
@Service
@RequiredArgsConstructor
@Slf4j
public class QueueListener {
    private final DeliveryClient deliveryClient;
    private final DeliveryService deliveryService;

    @RabbitListener(queues = "orderToDelivery")
    @Transactional
    public void handleOrderToDelivery(OrderActionDto orderAction) {
        orderAction.setStatus(OrderStatus.DELIVERY_PENDING);
        deliveryClient.updateOrderStatus(orderAction);
        deliveryService.notifyCouriers(orderAction.getOrderId());
        log.info("Order id={} send to delivery", orderAction.getOrderId());
    }
}
