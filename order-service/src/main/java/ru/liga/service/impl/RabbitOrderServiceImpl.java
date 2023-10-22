package ru.liga.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderInfoDto;
import ru.liga.service.RabbitOrderService;

@Service
@AllArgsConstructor
public class RabbitOrderServiceImpl implements RabbitOrderService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendOrderToDelivery(OrderInfoDto order) {
        rabbitTemplate.convertAndSend("directExchange", "delivery", serializeOrder(order));
    }

    @SneakyThrows
    private String serializeOrder(OrderInfoDto order) {
        try {
            return objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new Exception("Message cannot be serialized");
        }
    }
}
