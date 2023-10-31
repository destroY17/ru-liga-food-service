package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Информация о местонахождении ресторана")
public class RestaurantDto {
    private String name;
    private String address;
}
