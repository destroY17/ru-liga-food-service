package ru.liga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDeliveryDto {
    private String address;
    private double distance;
}
