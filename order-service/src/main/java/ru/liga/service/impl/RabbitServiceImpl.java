package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderActionDto;
import ru.liga.service.RabbitService;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitServiceImpl implements RabbitService {
    private final RabbitTemplate rabbitTemplate;

    public void sendNewOrder(OrderActionDto orderAction, String routingKey) {
        rabbitTemplate.convertAndSend("directExchange", routingKey, orderAction);

        log.info("Order id={} status={} send to notification-service", orderAction.getOrderId(),
                orderAction.getStatus());
    }
}
