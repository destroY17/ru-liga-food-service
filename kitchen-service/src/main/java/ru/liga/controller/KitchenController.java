package ru.liga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.liga.service.KitchenService;

@RestController
@RequestMapping("/kitchen-service")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API обработки заказа и его подготовки к доставке")
public class KitchenController {
    private final KitchenService kitchenService;

    @Operation(summary = "Принять заказ")
    @PostMapping("/accept/{orderId}")
    public void acceptOrder(@PathVariable Long orderId) {
        log.info("Received POST request to accept order id={}", orderId);
        kitchenService.acceptOrder(orderId);
    }

    @Operation(summary = "Отменить заказ")
    @PostMapping("/deny/{orderId}")
    public void denyOrder(@PathVariable Long orderId) {
        log.info("Received POST request to deny order id={}", orderId);
        kitchenService.denyOrder(orderId);
    }

    @Operation(summary = "Завершение подготовки заказа")
    @PostMapping("/complete/{orderId}")
    public void completeOrder(@PathVariable Long orderId) {
        log.info("Received POST request to complete order id={}", orderId);
        kitchenService.completeOrder(orderId, "kitchenToNotification");
    }
}
