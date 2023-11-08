package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "Сведения о новом товаре меню")
public class NewMenuItemDto {
    @NotNull
    @JsonProperty("restaurant_id")
    private Long restaurantId;
    @NotBlank
    private String name;
    @Positive
    private BigDecimal price;
    @NotBlank
    private String image;
    @NotBlank
    private String description;
}
