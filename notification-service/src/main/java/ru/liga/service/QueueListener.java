package ru.liga.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderActionDto;

@EnableRabbit
@Service
@RequiredArgsConstructor
@Slf4j
public class QueueListener {
    private final RabbitTemplate rabbitTemplate;

    private final static String NEW_ORDER_QUEUE = "newOrderToNotification";
    private final static String KITCHEN_ACCEPT_QUEUE = "kitchenAcceptToNotification";
    private final static String KITCHEN_DENIED_QUEUE = "kitchenDeniedToNotification";
    private final static String KITCHEN_COMPLETE_QUEUE = "kitchenCompleteToNotification";

    private final static String ORDER_SERVICE_TO_KITCHEN = "newOrderToKitchen";
    private final static String KITCHEN_TO_ORDER_SERVICE = "kitchenToOrder";
    private final static String KITCHEN_TO_DELIVERY = "orderToDelivery";

    @RabbitListener(queues = NEW_ORDER_QUEUE)
    public void handleNewOrder(OrderActionDto orderAction) {
        rabbitTemplate.convertAndSend(ORDER_SERVICE_TO_KITCHEN, orderAction);
        log.info("Order id={} send to kitchen", orderAction.getOrderId());
    }

    @RabbitListener(queues = {KITCHEN_ACCEPT_QUEUE, KITCHEN_DENIED_QUEUE})
    public void handleKitchenAnswerForNewOrder(OrderActionDto orderAction) {
        rabbitTemplate.convertAndSend(KITCHEN_TO_ORDER_SERVICE, orderAction);
        log.info("Order id={} status={} send to order service", orderAction.getOrderId(),
                orderAction.getStatus());
    }

    @RabbitListener(queues = KITCHEN_COMPLETE_QUEUE)
    public void handleCompleteOrder(OrderActionDto orderAction) {
        rabbitTemplate.convertAndSend(KITCHEN_TO_ORDER_SERVICE, orderAction);
        rabbitTemplate.convertAndSend(KITCHEN_TO_DELIVERY, orderAction);
        log.info("Order id={} status={} send to order and delivery services", orderAction.getOrderId(),
                orderAction.getStatus());
    }
}
