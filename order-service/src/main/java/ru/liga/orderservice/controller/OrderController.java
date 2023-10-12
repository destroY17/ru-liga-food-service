package ru.liga.orderservice.controller;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.dto.NewOrderDto;
import ru.liga.orderservice.dto.OrderDto;
import ru.liga.orderservice.dto.DeliveryOrderDto;
import ru.liga.orderservice.model.OrderStatus;
import ru.liga.orderservice.service.OrderService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/orders")
    public List<OrderDto> findAllOrders(@PositiveOrZero @RequestParam(defaultValue = "0") Integer pageIndex,
                                        @Positive @RequestParam(defaultValue = "10") Integer pageCount,
                                        @RequestParam(name = "status", required = false) OrderStatus status) {
        Pageable page = PageRequest.of(pageIndex, pageCount);
        return orderService.findAllOrders(page, status);
    }

    @GetMapping("/order/{id}")
    public OrderDto findOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @PostMapping("/order")
    public DeliveryOrderDto addOrder(@Valid @RequestBody NewOrderDto newOrder) {
        return orderService.addOrder(newOrder);
    }
}
