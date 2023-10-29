package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderActionDto;
import ru.liga.service.KitchenService;

@EnableRabbit
@Service
@RequiredArgsConstructor
@Slf4j
public class QueueListener {
    private final KitchenService kitchenService;

    @RabbitListener(queues = "newOrderToKitchen")
    public void handleNewOrder(OrderActionDto orderAction) {
        kitchenService.acceptOrder(orderAction.getOrderId());
        log.info("Order id={} accepted by kitchen", orderAction.getOrderId());
    }
}
