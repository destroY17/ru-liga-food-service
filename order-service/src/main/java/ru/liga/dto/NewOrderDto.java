package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class NewOrderDto {
    @NotNull
    @JsonProperty("restaurant_id")
    private Long restaurantId;
    @NotEmpty
    @JsonProperty("menu_items")
    private List<OrderItemDto> menuItems;
}
