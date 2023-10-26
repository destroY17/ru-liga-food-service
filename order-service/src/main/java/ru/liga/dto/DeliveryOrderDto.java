package ru.liga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DeliveryOrderDto {
    @NotNull
    private Long id;
    @NotBlank
    private String secretPaymentUrl;
    private LocalDateTime estimatedTimeOfArrival;
}
