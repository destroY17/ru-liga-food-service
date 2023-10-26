package ru.liga.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.OrderActionDto;
import ru.liga.service.KitchenService;

import javax.validation.Valid;

@RestController
@RequestMapping("/kitchen")
@RequiredArgsConstructor
@Slf4j
public class KitchenController {
    private final KitchenService kitchenService;

    @PostMapping("/accept/{orderId}")
    public void acceptOrder(@PathVariable Long orderId) {
        log.info("Received POST request to accept order id={}", orderId);
        kitchenService.acceptOrder(orderId);
    }

    @PostMapping("/deny/{orderId}")
    public void denyOrder(@PathVariable Long orderId) {
        log.info("Received POST request to deny order id={}", orderId);
        kitchenService.denyOrder(orderId);
    }

    @PostMapping("/complete/{orderId}")
    public void completeOrder(@PathVariable Long orderId) {
        log.info("Received POST request to complete order id={}", orderId);
        kitchenService.completeOrder(orderId, "delivery");
    }

    @PostMapping("/update")
    public void updateOrderStatus(@Valid @RequestBody OrderActionDto orderActionDto) {
        log.info("Received POST request to update status of order id={}, updated status={}",
                orderActionDto.getOrderId(), orderActionDto.getStatus());
        kitchenService.updateOrderStatus(orderActionDto);
    }

}
















