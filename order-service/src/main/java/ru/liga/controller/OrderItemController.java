package ru.liga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Api работы с товарами заказа")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @Operation(summary = "Добавить")
    @PostMapping
    public OrderItem addOrderItem(@Valid @RequestBody NewOrderItemDto newOrderItemDto) {
        log.info("Received POST request to add new order item:{}", newOrderItemDto.toString());
        return orderItemService.addOrderItem(newOrderItemDto);
    }

    @Operation(summary = "Удалить по идентификатору")
    @DeleteMapping("/{id}")
    public void deleteOrderItemById(@PathVariable Long id) {
        log.info("Received DELETE request to delete order item id={}", id);
        orderItemService.deleteOrderItemById(id);
    }
}