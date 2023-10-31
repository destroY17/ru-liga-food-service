package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(name = "Информация о заказе")
public class OrderInfoDto {
    @NotNull
    private Long id;
    private CustomerDeliveryDto customer;
    private RestaurantDto restaurant;
    private Timestamp timestamp;
    private List<MenuItemInOrderDto> items;
}
