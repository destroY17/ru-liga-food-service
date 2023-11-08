package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.dto.OrderActionDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.queue.KitchenQueue;
import ru.liga.repository.OrderRepository;
import ru.liga.service.KitchenService;
import ru.liga.util.OrderUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KitchenServiceImpl implements KitchenService {
    private final RabbitService rabbitService;
    private final OrderRepository orderRepository;

    @Override
    public void acceptOrder(UUID orderId) {
        correctStatus(orderId, OrderStatus.CUSTOMER_PAID);

        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.KITCHEN_ACCEPTED);

        orderRepository.updateOrderByStatus(orderId, orderAction.getStatus());
        rabbitService.sendOrder(orderAction, KitchenQueue.KITCHEN_ACCEPT);
    }

    @Override
    public void completeOrder(UUID orderId) {
        correctStatus(orderId, OrderStatus.KITCHEN_ACCEPTED);

        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.KITCHEN_PREPARING);
        orderRepository.updateOrderByStatus(orderId, orderAction.getStatus());

        rabbitService.sendOrder(orderAction, KitchenQueue.KITCHEN_COMPLETE);
    }

    @Override
    public void denyOrder(UUID orderId) {
        correctStatus(orderId, OrderStatus.CUSTOMER_PAID);

        OrderActionDto orderAction = new OrderActionDto(orderId, OrderStatus.KITCHEN_DENIED);
        orderRepository.updateOrderByStatus(orderId, orderAction.getStatus());

        rabbitService.sendOrder(orderAction, KitchenQueue.KITCHEN_DENIED);
    }

    private void correctStatus(UUID orderId, OrderStatus correctStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order id=" + orderId+ " not found"));
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), correctStatus);
    }
}
