package ru.liga.deliveryservice.dto;

import lombok.Data;

@Data
public class DeliveryDto {
    private Long orderId;
    private RestaurantDto restaurant;
    private CustomerDto customer;
    private double payment;
}
