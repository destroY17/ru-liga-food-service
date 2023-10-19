package ru.liga.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDto {
    private Long orderId;
    private RestaurantDto restaurant;
    private CustomerDto customer;
    private double payment;
}
