package ru.liga.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.batismapper.CourierMapper;
import ru.liga.client.DeliveryClient;
import ru.liga.dto.OrderActionDto;
import ru.liga.model.*;

import java.util.List;

@EnableRabbit
@Service
@RequiredArgsConstructor
@Slf4j
public class QueueListener {
    private final ObjectMapper objectMapper;
    private final CourierMapper courierMapper;
    private final DeliveryClient deliveryClient;

    //TODO: change logic to send push all of couriers
    @RabbitListener(queues = "delivery")
    public void handleNewOrder(String orderInfo) {
        try {
            OrderActionDto orderAction = objectMapper.readValue(orderInfo, OrderActionDto.class);
            orderAction.setStatus(OrderStatus.DELIVERY_PENDING);
            deliveryClient.updateOrderStatus(orderAction);

            List<Courier> inactiveCouriers = courierMapper.findAllByStatus(CourierStatus.INACTIVE);

            log.info("Inactive couriers are notified of new order id={}", orderAction.getOrderId());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
