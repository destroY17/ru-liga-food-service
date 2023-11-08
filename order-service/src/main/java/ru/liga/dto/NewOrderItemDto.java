package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@AllArgsConstructor
@Schema(name = "Сведения о новом товаре в заказе")
public class NewOrderItemDto {
    @NotNull
    @JsonProperty("order_id")
    private UUID orderId;
    @NotNull
    @JsonProperty("restaurant_menu_item_id")
    private Long restaurantMenuItemId;
    @Positive
    private int quantity;
}
