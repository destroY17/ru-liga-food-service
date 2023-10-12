package ru.liga.orderservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private RestaurantDto restaurant;
    private LocalDateTime timestamp;
    private List<ItemDto> items;
}
