package ru.liga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.DeliveryDto;
import ru.liga.service.DeliveryService;

import java.util.UUID;

/**
 * Конторллер взаимодействия с заказами, готовыми к доставке
 */
@RestController
@RequestMapping("/delivery-service/deliveries")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API доставки заказа")
public class DeliveryController {
    private final DeliveryService deliveryService;

    /**
     * Поиск доступных для доставки заказов
     *
     * @param pageable настройка страниц
     * @return список заказов с пагинацией
     */
    @Operation(summary = "Найти доступные для доставки заказы")
    @GetMapping
    public Page<DeliveryDto> findAvailableDeliveries(@PageableDefault Pageable pageable) {
        log.info("Received GET request to find available deliveries");
        return deliveryService.findAvailableDeliveries(pageable);
    }

    /**
     * Собрать заказ для подготовки к доставке
     *
     * @param orderId идентификатор заказа
     */
    @Operation(summary = "Собрать заказ")
    @PostMapping("/pick/{orderId}")
    public void pickOrder(@PathVariable UUID orderId) {
        log.info("Received POST request to picking order id={}", orderId);
        deliveryService.pickOrder(orderId);
    }
}

















