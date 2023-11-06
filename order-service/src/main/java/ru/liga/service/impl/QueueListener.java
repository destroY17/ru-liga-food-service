package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderActionDto;
import ru.liga.model.OrderStatus;
import ru.liga.service.OrderService;

@EnableRabbit
@Service
@RequiredArgsConstructor
@Slf4j
public class QueueListener {
    private final OrderService orderService;

    @RabbitListener(queues = "kitchenToOrder")
    public void handleInfoFromKitchen(OrderActionDto orderAction) {
        if (orderAction.getStatus() == OrderStatus.KITCHEN_DENIED) {
            orderService.refundOfFunds(orderAction.getOrderId());
        }

        log.info("Order id={} status{} info received", orderAction.getOrderId(),
                orderAction.getStatus());
    }
}
