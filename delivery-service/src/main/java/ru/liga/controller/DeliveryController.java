package ru.liga.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.DeliveryDto;
import ru.liga.model.Courier;
import ru.liga.service.DeliveryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping("/deliveries")
    public Page<DeliveryDto> findAvailableDeliveries(@PageableDefault Pageable pageable) {
        log.info("Received GET request to find available deliveries");
        return deliveryService.findAvailableDeliveries(pageable);
    }

    @GetMapping("/couriers")
    public List<Courier> findAllCouriers() {
        log.info("Received GET request to find all couriers");
        return deliveryService.findAllCouriers();
    }

    @GetMapping("couriers/{id}")
    public Courier findCourierById(@PathVariable Long id) {
        log.info("Received GET request to find courier id={}", id);
        return deliveryService.findCourierById(id);
    }
}

















