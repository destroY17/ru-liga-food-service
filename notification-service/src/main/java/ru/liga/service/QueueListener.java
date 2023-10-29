package ru.liga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderActionDto;

@EnableRabbit
@Service
@RequiredArgsConstructor
@Slf4j
public class QueueListener {
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "newOrderToNotification")
    public void handleNewOrder(OrderActionDto orderAction) {
        rabbitTemplate.convertAndSend("newOrderToKitchen", orderAction);
        log.info("Order id={} send to kitchen", orderAction.getOrderId());
    }

    @RabbitListener(queues = "kitchenToNotification")
    public void handleCompletedOrder(OrderActionDto orderAction) {
        rabbitTemplate.convertAndSend("orderToDelivery", orderAction);
        log.info("Order id={} send to delivery", orderAction.getOrderId());
    }
}
