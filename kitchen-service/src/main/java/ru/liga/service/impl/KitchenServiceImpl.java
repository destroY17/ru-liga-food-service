package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.client.KitchenClient;
import ru.liga.dto.OrderActionDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import ru.liga.service.KitchenService;
import ru.liga.util.OrderUtil;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KitchenServiceImpl implements KitchenService {
    private final RabbitService rabbitService;
    private final OrderRepository orderRepository;
    private final KitchenClient kitchenClient;

    @Override
    public void acceptOrder(Long orderId) {
        correctStatus(orderId, OrderStatus.CUSTOMER_PAID);

        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.KITCHEN_ACCEPTED);
        //kitchenClient.updateOrderStatus(orderAction);
        orderRepository.updateOrderByStatus(orderId, OrderStatus.KITCHEN_ACCEPTED);

        rabbitService.sendOrder(orderAction, "kitchenAcceptToNotification");
    }

    @Override
    public void completeOrder(Long orderId, String routingKey) {
        correctStatus(orderId, OrderStatus.KITCHEN_ACCEPTED);

        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.KITCHEN_PREPARING);
        //kitchenClient.updateOrderStatus(orderAction);
        orderRepository.updateOrderByStatus(orderId, OrderStatus.KITCHEN_PREPARING);

        rabbitService.sendOrder(orderAction, "kitchenCompleteToNotification");
    }

    @Override
    public void denyOrder(Long orderId) {
        correctStatus(orderId, OrderStatus.CUSTOMER_PAID);

        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.KITCHEN_DENIED);
        //kitchenClient.updateOrderStatus(orderAction);
        orderRepository.updateOrderByStatus(orderId, OrderStatus.KITCHEN_DENIED);

        rabbitService.sendOrder(orderAction, "kitchenDeniedToNotification");
    }

    private void correctStatus(Long orderId, OrderStatus correctStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Order id=%d not found",orderId)));
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), correctStatus);
    }
}
