package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.model.OrderStatus;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Данные о статусе заказа")
public class OrderActionDto {
    @NotNull
    @JsonProperty("order_id")
    private UUID orderId;
    @JsonProperty("order_status")
    private OrderStatus status;
}