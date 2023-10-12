package ru.liga.orderservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryOrderDto {
    private Long id;
    private String secretPaymentUrl;
    private LocalDateTime estimatedTimeOfArrival;
}
