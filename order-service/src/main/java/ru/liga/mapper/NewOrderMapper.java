package ru.liga.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.dto.NewOrderDto;
import ru.liga.model.Order;
import ru.liga.model.OrderStatus;
import ru.liga.repository.CustomerRepository;
import ru.liga.repository.RestaurantRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NewOrderMapper {
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderItemMapper mapper;

    public Order toEntity(Long customerId, NewOrderDto newOrder) {
        return Order.builder()
                .customer(customerRepository.findById(customerId).orElseThrow())
                .restaurant(restaurantRepository.findById(newOrder.getRestaurantId()).orElseThrow())
                .status(OrderStatus.CUSTOMER_CREATED)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .orderItems(mapper.toDto(newOrder.getMenuItems()))
                .build();
    }
}














