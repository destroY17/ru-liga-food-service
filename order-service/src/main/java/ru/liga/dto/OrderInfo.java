package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "Сведения о заказе для клиента")
public class OrderInfo {
    @JsonProperty("order_id")
    private UUID orderId;
    private RestaurantDto restaurant;
    private Timestamp timestamp;
    private List<MenuItemInOrderDto> items;
}
