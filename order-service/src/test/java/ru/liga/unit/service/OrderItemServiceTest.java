package ru.liga.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.dto.NewOrderItemDto;
import ru.liga.model.Order;
import ru.liga.model.OrderItem;
import ru.liga.model.RestaurantMenuItem;
import ru.liga.repository.MenuItemRepository;
import ru.liga.repository.OrderItemRepository;
import ru.liga.repository.OrderRepository;
import ru.liga.service.impl.OrderItemServiceImpl;

import java.util.Optional;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {
    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testAddOrderItem() {
        NewOrderItemDto newOrderItemDto = new NewOrderItemDto(UUID.randomUUID(), 1L, 2);

        Order order = new Order();
        RestaurantMenuItem menuItem = new RestaurantMenuItem();
        menuItem.setPrice(BigDecimal.valueOf(5.0));
        OrderItem savedOrderItem = new OrderItem();

        when(orderRepository.findById(newOrderItemDto.getOrderId())).thenReturn(Optional.of(order));
        when(menuItemRepository.findById(newOrderItemDto.getRestaurantMenuItemId())).thenReturn(Optional.of(menuItem));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(savedOrderItem);

        OrderItem result = orderItemService.addOrderItem(newOrderItemDto);

        verify(orderRepository).findById(newOrderItemDto.getOrderId());
        verify(menuItemRepository).findById(newOrderItemDto.getRestaurantMenuItemId());
        verify(orderItemRepository).save(argThat(orderItem -> {
            // Проверка, что сохраненный order item имеет корректные поля
            return orderItem.getOrder().equals(order) &&
                    orderItem.getRestaurantMenuItem().equals(menuItem) &&
                    orderItem.getPrice().equals(menuItem.getPrice().multiply(BigDecimal.valueOf(newOrderItemDto.getQuantity()))) &&
                    orderItem.getQuantity() == newOrderItemDto.getQuantity();
        }));

        assertNotNull(result);
    }

    @Test
    public void testDeleteOrderItemById() {
        Long orderItemId = 1L;

        orderItemService.deleteOrderItemById(orderItemId);

        verify(orderItemRepository).deleteById(orderItemId);
    }
}