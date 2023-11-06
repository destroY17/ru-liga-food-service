package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderActionDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitService {
    private final RabbitTemplate rabbitTemplate;

    public void sendOrder(OrderActionDto orderAction, String routingKey) {
        rabbitTemplate.convertAndSend("directExchange", routingKey, orderAction);
        log.info("Order id={} status={} send with routing key={}", orderAction.getOrderId(),
                orderAction.getStatus(), routingKey);
    }
}
