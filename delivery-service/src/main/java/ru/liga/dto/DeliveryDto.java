package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@Schema(name = "Сведения о заказе для курьера")
public class DeliveryDto {
    @NotNull
    private Long orderId;
    private RestaurantDeliveryDto restaurant;
    private CustomerDeliveryDto customer;
    @Positive
    private double payment;
}
