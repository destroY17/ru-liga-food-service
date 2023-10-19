package ru.liga.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMenuItemDto {
    @JsonProperty("restaurant_id")
    private Long restaurantId;
    private String name;
    private BigDecimal price;
    private String image;
    private String description;
}
