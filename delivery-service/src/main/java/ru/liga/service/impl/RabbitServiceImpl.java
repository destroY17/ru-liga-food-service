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

    @Override
    public void sendDeliveryStatus(OrderActionDto orderAction, String deliveryAction) {
        rabbitTemplate.convertAndSend("directExchange", deliveryAction, orderAction);
        log.info("Delivery id={} status={} send to notification service", orderAction.getOrderId(),
                orderAction.getStatus());
    }

    @Override
    public void sendDeliveryInfo(Long orderId, String deliveryAction) {
        rabbitTemplate.convertAndSend("directExchange", deliveryAction, orderId);
        log.info("Delivery info id={} send to notification-service", orderId);
    }
}
