package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Сведения о местонахождении ресторана относительно курьера")
public class RestaurantDeliveryDto {
    private String address;
    private double distance;
}
