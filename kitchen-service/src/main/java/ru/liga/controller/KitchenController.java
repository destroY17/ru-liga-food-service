package ru.liga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.liga.service.KitchenService;

import java.util.UUID;

/**
 * Контроллер приготовления заказа
 */
@RestController
@RequestMapping("/kitchen-service")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API приготовления заказа")
public class KitchenController {
    private final KitchenService kitchenService;

    /**
     * Принять заказ для приготовления
     * @param orderId идентификатор заказа
     */
    @Operation(summary = "Принять заказ")
    @PostMapping("/accept/{orderId}")
    public void acceptOrder(@PathVariable UUID orderId) {
        log.info("Received POST request to accept order id={}", orderId);
        kitchenService.acceptOrder(orderId);
    }

    /**
     * Отклонить заказ
     * @param orderId идентификатор заказа
     */
    @Operation(summary = "Отклонить заказ")
    @PostMapping("/deny/{orderId}")
    public void denyOrder(@PathVariable UUID orderId) {
        log.info("Received POST request to deny order id={}", orderId);
        kitchenService.denyOrder(orderId);
    }

    /**
     * Завершить приготовление заказа, подготовка к доставке
     * @param orderId идентификатор заказа
     */
    @Operation(summary = "Завершение подготовки заказа")
    @PostMapping("/complete/{orderId}")
    public void completeOrder(@PathVariable UUID orderId) {
        log.info("Received POST request to complete order id={}", orderId);
        kitchenService.completeOrder(orderId, "kitchenToNotification");
    }
}
