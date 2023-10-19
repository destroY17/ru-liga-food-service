package ru.liga.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DeliveryOrderDto {
    private Long id;
    private String secretPaymentUrl;
    private LocalDateTime estimatedTimeOfArrival;
}
