package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderActionDto;

@EnableRabbit
@Service
@RequiredArgsConstructor
@Slf4j
public class QueueListener {
    @RabbitListener(queues = "newOrderToKitchen")
    public void handleNewOrder(OrderActionDto orderAction) {
        log.info("New order id={} status={} has arrived", orderAction.getOrderId(), orderAction.getStatus());
    }
}
