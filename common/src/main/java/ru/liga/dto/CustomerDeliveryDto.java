package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Данные о местоположении клиента")
public class CustomerDeliveryDto {
    private String address;
    private double distance;
}
