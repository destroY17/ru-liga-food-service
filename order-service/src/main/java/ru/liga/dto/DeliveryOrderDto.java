package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "Сведения о доставки заказа для покупателя")
public class DeliveryOrderDto {
    @NotNull
    private UUID id;
    @Schema(name = "URL для оплаты заказа")
    @NotBlank
    private String secretPaymentUrl;
    @Schema(name = "Ожидаемое время прибытия")
    private LocalDateTime estimatedTimeOfArrival;
}
