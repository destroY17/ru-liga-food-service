package ru.liga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderActionDto;

import java.util.UUID;

import static ru.liga.queue.ListenQueue.*;
import static ru.liga.queue.NotifyQueue.*;

@EnableRabbit
@Service
@RequiredArgsConstructor
@Slf4j
public class QueueListener {
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = NEW_ORDER_QUEUE)
    public void handleNewOrder(OrderActionDto orderAction) {
        rabbitTemplate.convertAndSend(ORDER_SERVICE_TO_KITCHEN, orderAction);
        log.info("Order id={} send to kitchen", orderAction.getOrderId());
    }

    @RabbitListener(queues = {KITCHEN_ACCEPT_QUEUE, KITCHEN_DENIED_QUEUE})
    public void handleKitchenAnswerForNewOrder(OrderActionDto orderAction) {
        rabbitTemplate.convertAndSend(KITCHEN_TO_ORDER_SERVICE, orderAction);
        log.info("Order id={} status={} send to order service", orderAction.getOrderId(),
                orderAction.getStatus());
    }

    @RabbitListener(queues = KITCHEN_COMPLETE_QUEUE)
    public void handleCompleteOrder(OrderActionDto orderAction) {
        rabbitTemplate.convertAndSend(KITCHEN_TO_ORDER_SERVICE, orderAction);
        rabbitTemplate.convertAndSend(KITCHEN_TO_DELIVERY, orderAction);
        log.info("Order id={} status={} send to order and delivery services", orderAction.getOrderId(),
                orderAction.getStatus());
    }

    @RabbitListener(queues = {DELIVERY_TAKEN_QUEUE, DELIVERY_COMPLETE_QUEUE})
    public void handleDelivery(OrderActionDto orderAction) {
        rabbitTemplate.convertAndSend(DELIVERY_TO_ORDER, orderAction);
        log.info("Order id={} status={} send to order service", orderAction.getOrderId(),
                orderAction.getStatus());
    }

    @RabbitListener(queues = NOTIFY_COURIERS_QUEUE)
    public void handleNotifyCouriers(UUID orderId) {
        //Imitation notify for couriers
        System.out.println("Order id=" + orderId + " is available to delivery");
        log.info("Couriers are notified about order id={}", orderId);
    }
}
