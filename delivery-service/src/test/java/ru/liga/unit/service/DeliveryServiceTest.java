package ru.liga.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.liga.dto.DeliveryDto;
import ru.liga.mapper.DeliveryToOrderMapper;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import ru.liga.service.impl.DeliveryServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DeliveryToOrderMapper deliveryToOrderMapper;

    @Test
    public void testFindAvailableDeliveries() {
        Pageable pageable = Pageable.unpaged();
        Page<Order> deliveryOrders = createSampleDeliveryOrders();
        List<DeliveryDto> deliveryDtos = createSampleDeliveryDtos();

        when(orderRepository.findOrdersByStatus(pageable, OrderStatus.DELIVERY_PENDING)).thenReturn(deliveryOrders);
        when(deliveryToOrderMapper.toDto(deliveryOrders.getContent())).thenReturn(deliveryDtos);

        Page<DeliveryDto> result = deliveryService.findAvailableDeliveries(pageable);

        verify(orderRepository).findOrdersByStatus(pageable, OrderStatus.DELIVERY_PENDING);
        verify(deliveryToOrderMapper).toDto(deliveryOrders.getContent());

        assertNotNull(result);
        assertEquals(deliveryDtos, result.getContent());
    }

    @Test
    public void testPickOrder() {
        UUID orderId = UUID.randomUUID();
        Order order = createSampleOrder(OrderStatus.DELIVERY_PENDING);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertDoesNotThrow(() -> deliveryService.pickOrder(orderId));

        verify(orderRepository).findById(orderId);
        verify(orderRepository).updateOrderByStatus(orderId, OrderStatus.DELIVERY_PICKING);
    }

    private Page<Order> createSampleDeliveryOrders() {
        List<Order> orders = List.of(createSampleOrder(OrderStatus.DELIVERY_PENDING));
        return new PageImpl<>(orders);
    }

    private List<DeliveryDto> createSampleDeliveryDtos() {
        DeliveryDto dto = DeliveryDto.builder().build();
        return List.of(dto);
    }

    private Order createSampleOrder(OrderStatus status) {
        Order order = new Order();
        order.setStatus(status);
        return order;
    }
}
