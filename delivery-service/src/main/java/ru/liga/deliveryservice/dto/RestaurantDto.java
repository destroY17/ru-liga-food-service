package ru.liga.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantDto {
    private String address;
    private double distance;
}
