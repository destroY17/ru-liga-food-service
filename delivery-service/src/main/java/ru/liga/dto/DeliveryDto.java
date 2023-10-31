package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Сведения о заказе для доставки")
public class DeliveryDto {
    private Long orderId;
    private RestaurantDeliveryDto restaurant;
    private CustomerDeliveryDto customer;
    private double payment;
}
