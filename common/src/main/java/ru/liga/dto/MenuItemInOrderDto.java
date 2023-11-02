package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Данные товара из меню, включенного в заказ")
public class MenuItemInOrderDto {
    private BigDecimal price;
    private int quantity;
    private String description;
    private String image;
}
