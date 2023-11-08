package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.client.DeliveryClient;
import ru.liga.dto.DeliveryDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.mapper.DeliveryToOrderMapper;

import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import ru.liga.service.DeliveryService;
import ru.liga.util.OrderUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {
    private final OrderRepository orderRepository;
    private final DeliveryToOrderMapper deliveryToOrderMapper;
    private final DeliveryClient deliveryClient;

    @Override
    public Page<DeliveryDto> findAvailableDeliveries(Pageable pageable) {
        Page<Order> deliveries = orderRepository.findOrdersByStatus(pageable, OrderStatus.DELIVERY_PENDING);

        return new PageImpl<>(deliveryToOrderMapper.toDto(deliveries.getContent()),
                pageable, deliveries.getTotalElements());
    }

    @Override
    @Transactional
    public void pickOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order id=" + orderId + "not found"));
        OrderUtil.correctStatusOrElseThrow(order.getStatus(), OrderStatus.DELIVERY_PENDING);

        // deliveryClient.updateOrderStatus(new OrderActionDto(orderId, OrderStatus.DELIVERY_PICKING));
        orderRepository.updateOrderByStatus(orderId, OrderStatus.DELIVERY_PICKING);
    }
}
