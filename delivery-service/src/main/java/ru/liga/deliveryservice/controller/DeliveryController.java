package ru.liga.deliveryservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.liga.deliveryservice.dto.DeliveryDto;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@AllArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping
    public List<DeliveryDto> findAllDeliveries(@PageableDefault Pageable pageable) {
        return deliveryService.findAllDeliveries(pageable);
    }
}
