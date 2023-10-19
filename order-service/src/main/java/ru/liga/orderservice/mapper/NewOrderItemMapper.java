package ru.liga.orderservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.orderservice.dto.NewOrderItemDto;
import ru.liga.orderservice.exception.DataNotFoundException;
import ru.liga.orderservice.model.Order;
import ru.liga.orderservice.model.OrderItem;
import ru.liga.orderservice.model.RestaurantMenuItem;
import ru.liga.orderservice.repository.OrderRepository;
import ru.liga.orderservice.repository.MenuItemRepository;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class NewOrderItemMapper {
    private final MenuItemRepository restaurantRepository;
    private final OrderRepository orderRepository;

    public OrderItem mapToEntity(NewOrderItemDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order is not found"));
        RestaurantMenuItem menuItem = restaurantRepository.findById(dto.getRestaurantMenuItemId())
                .orElseThrow(() -> new DataNotFoundException("MenuItem is not found"));

        return OrderItem.builder()
                .order(order)
                .restaurantMenuItem(menuItem)
                .price(menuItem.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())))
                .quantity(dto.getQuantity())
                .build();
    }
}
