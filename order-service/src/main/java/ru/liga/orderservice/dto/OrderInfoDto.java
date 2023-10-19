package ru.liga.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderInfoDto {
    private Long id;
    private RestaurantDto restaurant;
    private LocalDateTime timestamp;
    private List<MenuItemInOrderDto> items;
}
