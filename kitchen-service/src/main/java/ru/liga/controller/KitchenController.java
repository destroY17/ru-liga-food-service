package ru.liga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.OrderActionDto;
import ru.liga.service.KitchenService;

import javax.validation.Valid;

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

    @Operation(summary = "Обновить статус заказа")
    @PostMapping("/update")
    public void updateOrderStatus(@Valid @RequestBody OrderActionDto orderActionDto) {
        log.info("Received POST request to update status of order id={}, updated status={}",
                orderActionDto.getOrderId(), orderActionDto.getStatus());
        kitchenService.updateOrderStatus(orderActionDto);
    }
}
