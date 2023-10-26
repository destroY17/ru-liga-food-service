package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class NewOrderItemDto {
    @NotNull
    @JsonProperty("order_id")
    private Long orderId;
    @NotNull
    @JsonProperty("restaurant_menu_item_id")
    private Long restaurantMenuItemId;
    @Positive
    private int quantity;
}
