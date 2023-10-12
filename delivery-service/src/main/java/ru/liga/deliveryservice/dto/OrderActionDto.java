package ru.liga.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.criterion.Order;
import ru.liga.deliveryservice.model.OrderAction;


@Data
public class OrderActionDto {
    @JsonProperty("order_action")
    private OrderAction orderAction;
}
