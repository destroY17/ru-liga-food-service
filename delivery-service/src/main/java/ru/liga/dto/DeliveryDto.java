package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "Сведения о заказе для курьера")
public class DeliveryDto {
    @NotNull
    private UUID orderId;
    private RestaurantDeliveryDto restaurant;
    private CustomerDeliveryDto customer;
    @Positive
    private double payment;
}
