package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class OrderItemDto {
    @Positive
    private int quantity;
    @NotNull
    @JsonProperty("menu_item_id")
    private Long menuItemId;
}
