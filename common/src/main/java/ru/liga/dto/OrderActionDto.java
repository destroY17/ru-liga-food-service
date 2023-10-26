package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.liga.model.OrderStatus;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
public class OrderActionDto {
    @NotNull
    @JsonProperty("order_id")
    private Long orderId;
    
    @JsonProperty("order_status")
    private OrderStatus status;
}