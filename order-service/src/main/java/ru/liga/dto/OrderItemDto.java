package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@Schema(description = "Сведения о товаре в заказе")
public class OrderItemDto {
    @Positive
    private int quantity;
    @NotNull
    @JsonProperty("menu_item_id")
    private Long menuItemId;
}
