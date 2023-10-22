package ru.liga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDto {
    private Long orderId;
    private RestaurantDeliveryDto restaurant;
    private CustomerDeliveryDto customer;
    private double payment;
}
