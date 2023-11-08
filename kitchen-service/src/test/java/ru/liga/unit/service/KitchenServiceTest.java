package ru.liga.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.dto.OrderActionDto;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.OrderRepository;
import ru.liga.service.impl.KitchenServiceImpl;
import ru.liga.service.impl.RabbitService;

import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KitchenServiceTest {
    @InjectMocks
    private KitchenServiceImpl kitchenService;

    @Mock
    private RabbitService rabbitService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testAcceptOrder() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setStatus(OrderStatus.CUSTOMER_PAID);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        kitchenService.acceptOrder(orderId);

        verify(orderRepository).findById(orderId);
        verify(orderRepository).updateOrderByStatus(orderId, OrderStatus.KITCHEN_ACCEPTED);
        verify(rabbitService).sendOrder(any(OrderActionDto.class), eq("kitchenAcceptToNotification"));
    }

    @Test
    public void testCompleteOrder() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setStatus(OrderStatus.KITCHEN_ACCEPTED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        kitchenService.completeOrder(orderId);

        verify(orderRepository).findById(orderId);
        verify(orderRepository).updateOrderByStatus(orderId, OrderStatus.KITCHEN_PREPARING);
        verify(rabbitService).sendOrder(any(OrderActionDto.class), eq("kitchenCompleteToNotification"));
    }

    @Test
    public void testDenyOrder() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setStatus(OrderStatus.CUSTOMER_PAID);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        kitchenService.denyOrder(orderId);

        verify(orderRepository).findById(orderId);
        verify(orderRepository).updateOrderByStatus(orderId, OrderStatus.KITCHEN_DENIED);
        verify(rabbitService).sendOrder(any(OrderActionDto.class), eq("kitchenDeniedToNotification"));
    }
}