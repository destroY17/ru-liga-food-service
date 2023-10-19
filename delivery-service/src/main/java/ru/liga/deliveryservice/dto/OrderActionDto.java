package ru.liga.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.liga.deliveryservice.model.OrderAction;


@Data
@AllArgsConstructor
public class OrderActionDto {
    @JsonProperty("order_action")
    private OrderAction orderAction;
}
