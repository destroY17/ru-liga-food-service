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

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderItem addOrderItem(NewOrderItemDto newOrderItemDto) {
        Order order = orderRepository.findById(newOrderItemDto.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order is not found"));
        RestaurantMenuItem menuItem = menuItemRepository.findById(newOrderItemDto.getRestaurantMenuItemId())
                .orElseThrow(() -> new DataNotFoundException("Menu item is not found"));

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
