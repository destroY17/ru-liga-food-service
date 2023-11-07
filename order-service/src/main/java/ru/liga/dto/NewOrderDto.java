package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "Сведения о новом заказе")
public class NewOrderDto {
    @NotNull
    @JsonProperty("restaurant_id")
    private Long restaurantId;
    @NotEmpty
    @JsonProperty("menu_items")
    private List<OrderItemDto> menuItems;
}
