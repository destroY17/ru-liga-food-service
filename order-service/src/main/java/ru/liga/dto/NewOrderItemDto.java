package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewOrderItemDto {
    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("restaurant_menu_item_id")
    private Long restaurantMenuItemId;
    private int quantity;
}
