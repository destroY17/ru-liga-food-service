package ru.liga.orderservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.dto.NewOrderDto;
import ru.liga.orderservice.dto.OrderInfoDto;
import ru.liga.orderservice.dto.DeliveryOrderDto;
import ru.liga.orderservice.service.OrderService;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public Page<OrderInfoDto> findAllOrders(@PageableDefault Pageable pageable) {
        return orderService.findAllOrders(pageable);
    }

    @GetMapping("/{id}")
    public OrderInfoDto findOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @PostMapping
    public DeliveryOrderDto addOrder(@Valid @RequestBody NewOrderDto newOrder) {
        return orderService.addOrder(newOrder);
    }
}
