package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.NewOrderItemDto;
import ru.liga.exception.DataNotFoundException;
import ru.liga.model.Order;
import ru.liga.model.OrderItem;
import ru.liga.model.RestaurantMenuItem;
import ru.liga.repository.MenuItemRepository;
import ru.liga.repository.OrderItemRepository;
import ru.liga.repository.OrderRepository;
import ru.liga.service.OrderItemService;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderItem addOrderItem(NewOrderItemDto newOrderItemDto) {
        UUID orderId = newOrderItemDto.getOrderId();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order id=" + orderId + " not found"));

        Long menuItemId = newOrderItemDto.getRestaurantMenuItemId();
        RestaurantMenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new DataNotFoundException("Menu item id=" + menuItemId + " not found"));

        return orderItemRepository.save(
                OrderItem.builder()
                        .order(order)
                        .restaurantMenuItem(menuItem)
                        .price(menuItem.getPrice().multiply(BigDecimal.valueOf(newOrderItemDto.getQuantity())))
                        .quantity(newOrderItemDto.getQuantity())
                        .build()
        );
    }

    @Override
    public void deleteOrderItemById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
