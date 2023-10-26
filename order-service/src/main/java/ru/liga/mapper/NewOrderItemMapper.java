package ru.liga.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.exception.DataNotFoundException;
import ru.liga.repository.MenuItemRepository;
import ru.liga.dto.NewOrderItemDto;
import ru.liga.model.Order;
import ru.liga.model.OrderItem;
import ru.liga.model.RestaurantMenuItem;
import ru.liga.repository.OrderRepository;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class NewOrderItemMapper {
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;

    public OrderItem toEntity(NewOrderItemDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order is not found"));
        RestaurantMenuItem menuItem = menuItemRepository.findById(dto.getRestaurantMenuItemId())
                .orElseThrow(() -> new DataNotFoundException("Menu item is not found"));

        return OrderItem.builder()
                .order(order)
                .restaurantMenuItem(menuItem)
                .price(menuItem.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())))
                .quantity(dto.getQuantity())
                .build();
    }
}
