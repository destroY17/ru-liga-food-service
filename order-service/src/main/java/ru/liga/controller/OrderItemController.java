package ru.liga.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.model.OrderItem;
import ru.liga.dto.NewOrderItemDto;
import ru.liga.service.OrderItemService;

@RestController
@RequestMapping("/order-items")
@AllArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping
    public OrderItem addOrderItem(@RequestBody NewOrderItemDto newOrderItemDto) {
        return orderItemService.addOrderItem(newOrderItemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItemById(@PathVariable Long id) {
        orderItemService.deleteOrderItemById(id);
    }
}