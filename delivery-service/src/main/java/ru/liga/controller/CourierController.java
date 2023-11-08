package ru.liga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.DeliveryDto;
import ru.liga.model.Courier;
import ru.liga.service.CourierService;

import java.util.List;

@RestController
@RequestMapping("/delivery-service/couriers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API работы с курьерами")
public class CourierController {
    private final CourierService courierService;

    @Operation(summary = "Найти всех курьеров")
    @GetMapping
    public List<Courier> findAllCouriers() {
        log.info("Received GET request to find all couriers");
        return courierService.findAllCouriers();
    }

    @Operation(summary = "Найти курьера по идентификатору")
    @GetMapping("/{id}")
    public Courier findCourierById(@PathVariable Long id) {
        log.info("Received GET request to find courier id={}", id);
        return courierService.findCourierById(id);
    }

    @Operation(summary = "Принять заказ")
    @PostMapping("{courierId}/take/{orderId}")
    public DeliveryDto takeOrder(@PathVariable Long courierId, @PathVariable Long orderId) {
        log.info("Received POST request to take order");
        return courierService.takeOrder(courierId, orderId);
    }

    @Operation(summary = "Завершить доставку заказа")
    @PostMapping("/complete/{orderId}")
    public void completeDelivery(@PathVariable Long orderId) {
        log.info("Received POST request to complete delivery");
        courierService.completeDelivery(orderId);
    }
}
