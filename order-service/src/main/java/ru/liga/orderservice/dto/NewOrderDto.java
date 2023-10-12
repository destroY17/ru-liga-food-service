package ru.liga.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderDto {
    @JsonProperty("restaurant_id")
    private Long restaurantId;
    @JsonProperty("menu_items")
    private List<MenuItemDto> menuItems;
}
