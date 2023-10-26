package ru.liga.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.liga.model.OrderItem;
import ru.liga.dto.NewOrderItemDto;
import ru.liga.service.OrderItemService;

import javax.validation.Valid;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
@Slf4j
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping
    public OrderItem addOrderItem(@Valid @RequestBody NewOrderItemDto newOrderItemDto) {
        log.info("Received POST request to add new order item:{}", newOrderItemDto.toString());
        return orderItemService.addOrderItem(newOrderItemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItemById(@PathVariable Long id) {
        log.info("Received DELETE request to delete order item id={}", id);
        orderItemService.deleteOrderItemById(id);
    }
}