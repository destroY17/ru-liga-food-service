package ru.liga.orderservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.dto.NewOrderItemDto;
import ru.liga.orderservice.model.OrderItem;
import ru.liga.orderservice.service.OrderItemsService;

@RestController
@RequestMapping("/order-items")
@AllArgsConstructor
public class OrderItemController {
    private final OrderItemsService orderItemsService;

    @PostMapping
    public OrderItem addOrderItem(@RequestBody NewOrderItemDto newOrderItemDto) {
        return orderItemsService.addOrderItem(newOrderItemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItemById(@PathVariable Long id) {
        orderItemsService.deleteOrderItemById(id);
    }
}
