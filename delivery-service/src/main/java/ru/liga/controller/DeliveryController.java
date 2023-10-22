package ru.liga.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.DeliveryDto;
import ru.liga.model.OrderStatus;
import ru.liga.service.DeliveryService;

@RestController
@RequestMapping("/deliveries")
@AllArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping
    public Page<DeliveryDto> findAllDeliveries(@PageableDefault Pageable pageable,
                                               @RequestParam OrderStatus status) {
        return deliveryService.findDeliveriesByStatus(pageable, status);
    }
}
