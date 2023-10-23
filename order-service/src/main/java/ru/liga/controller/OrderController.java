package ru.liga.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.NewOrderDto;
import ru.liga.dto.OrderInfoDto;
import ru.liga.dto.DeliveryOrderDto;
import ru.liga.model.OrderStatus;
import ru.liga.service.OrderService;
import ru.liga.service.RabbitOrderService;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final RabbitOrderService rabbitOrderService;

    @GetMapping
    public Page<OrderInfoDto> findOrdersByStatus(@PageableDefault Pageable pageable,
                                                 @RequestParam(required = false) OrderStatus status) {
        return status == null ?
                orderService.findAllOrders(pageable) :
                orderService.findOrdersByStatus(pageable, status);
    }

    //TODO:temporary implementation
    @PostMapping("/{id}/send/delivery")
    public ResponseEntity sendOrderToDelivery(@PathVariable Long id) {
        rabbitOrderService.sendOrderToDelivery(findOrderById(id));
        return ResponseEntity.ok().build();
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
