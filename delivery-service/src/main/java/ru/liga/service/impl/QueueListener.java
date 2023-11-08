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
import ru.liga.queue.DeliveryQueue;
import ru.liga.queue.ListenQueue;
import ru.liga.repository.OrderRepository;
import ru.liga.service.RabbitService;

@EnableRabbit
@Service
@RequiredArgsConstructor
@Slf4j
public class QueueListener {
    private final DeliveryClient deliveryClient;
    private final RabbitService rabbitService;
    private final OrderRepository orderRepository;

    @RabbitListener(queues = ListenQueue.ORDER_TO_DELIVERY)
    @Transactional
    public void handleOrderToDelivery(OrderActionDto orderAction) {
        orderAction.setStatus(OrderStatus.DELIVERY_PENDING);
        log.info("Order id={} has arrived to delivery", orderAction.getOrderId());

        //deliveryClient.updateOrderStatus(orderAction);
        orderRepository.updateOrderByStatus(orderAction.getOrderId(), orderAction.getStatus());
        rabbitService.sendDeliveryInfo(orderAction.getOrderId(), DeliveryQueue.NOTIFY_COURIERS);
    }
}
