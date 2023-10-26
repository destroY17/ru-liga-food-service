package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderActionDto;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import ru.liga.service.KitchenService;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenServiceImpl implements KitchenService {
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void acceptOrder(Long orderId) {
        updateOrderStatus(new OrderActionDto(orderId, OrderStatus.KITCHEN_ACCEPTED));
    }

    @Override
    public void completeOrder(Long orderId, String routingKey) {
        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.KITCHEN_PREPARING);
        updateOrderStatus(orderAction);
        rabbitTemplate.convertAndSend("directExchange", routingKey, orderAction);

        log.info("Order id={}, status={} has been sent for delivery",
                orderAction.getOrderId(), orderAction.getStatus());
    }

    @Override
    public void denyOrder(Long orderId) {
        updateOrderStatus(new OrderActionDto(orderId, OrderStatus.KITCHEN_DENIED));
    }

    @Override
    public void updateOrderStatus(OrderActionDto orderActionDto) {
        orderRepository.updateOrderByStatus(orderActionDto.getOrderId(), orderActionDto.getStatus());
    }
}
