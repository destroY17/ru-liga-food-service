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
import java.util.UUID;

/**
 * Контроллер для взамодействия с курьерами
 */
@RestController
@RequestMapping("/delivery-service/couriers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API работы с курьерами")
public class CourierController {
    private final CourierService courierService;

    /**
     * Найти всех курьеров
     * @return список курьеров
     */
    @Operation(summary = "Найти всех курьеров")
    @GetMapping
    public List<Courier> findAllCouriers() {
        log.info("Received GET request to find all couriers");
        return courierService.findAllCouriers();
    }

    /**
     * Найти курьера по идентификатору
     * @param id идентификатор курьера
     * @return курьер
     */
    @Operation(summary = "Найти курьера по идентификатору")
    @GetMapping("/{id}")
    public Courier findCourierById(@PathVariable Long id) {
        log.info("Received GET request to find courier id={}", id);
        return courierService.findCourierById(id);
    }

    /**
     * Принятие курьером заказа для доставки
     * @param courierId идентификатор курьера
     * @param orderId идентификатор заказа
     * @return сведения о заказе, необходимые курьеру
     */
    @Operation(summary = "Принять заказ")
    @PostMapping("{courierId}/take/{orderId}")
    public DeliveryDto takeOrder(@PathVariable Long courierId, @PathVariable UUID orderId) {
        log.info("Received POST request to take order");
        return courierService.takeOrder(courierId, orderId);
    }

    /**
     * Завершение доставки заказа
     * @param orderId идентификатор заказа
     */
    @Operation(summary = "Завершить доставку заказа")
    @PostMapping("/complete/{orderId}")
    public void completeDelivery(@PathVariable UUID orderId) {
        log.info("Received POST request to complete delivery");
        courierService.completeDelivery(orderId);
    }
}
