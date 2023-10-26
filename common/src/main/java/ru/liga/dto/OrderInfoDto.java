package ru.liga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderInfoDto {
    @NotNull
    private Long id;
    private CustomerDeliveryDto customer;
    private RestaurantDto restaurant;
    private Timestamp timestamp;
    private List<MenuItemInOrderDto> items;
}
