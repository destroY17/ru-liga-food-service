package ru.liga.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QueueListener {
    private final ObjectMapper objectMapper;

    //TODO: fix parsing
    @RabbitListener(queues = "delivery")
    public void handleDeliveryQueue(String orderInfo) {
//        try {
//            OrderInfoDto order = objectMapper.readValue(orderInfo, OrderInfoDto.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }
}
