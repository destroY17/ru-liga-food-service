package ru.liga.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QueueListener {
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "delivery")
    public void handleDeliveryQueue(String orderInfo) {
    }
}
