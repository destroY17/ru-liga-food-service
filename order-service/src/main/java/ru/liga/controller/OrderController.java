package ru.liga.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.NewOrderDto;
import ru.liga.dto.OrderInfoDto;
import ru.liga.dto.DeliveryOrderDto;
import ru.liga.model.OrderStatus;
import ru.liga.service.OrderService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public Page<OrderInfoDto> findOrdersByStatus(@PageableDefault Pageable pageable,
                                                 @RequestParam(required = false) OrderStatus status) {
        log.info("Received GET request to find orders by status={}", status);

        return Optional.ofNullable(status)
                .map(s -> orderService.findOrdersByStatus(pageable, s))
                .orElse(orderService.findAllOrders(pageable));
    }

    @GetMapping("/{id}")
    public OrderInfoDto findOrderById(@PathVariable Long id) {
        log.info("Received GET request to find order by id={}", id);
        return orderService.findOrderById(id);
    }

    @PostMapping
    public DeliveryOrderDto addOrder(@RequestParam Long customerId,
                                     @Valid @RequestBody NewOrderDto newOrder) {
        log.info("Received POST request to add new order:{} by customer (id={})",
                newOrder.toString(),customerId);
        return orderService.addOrder(customerId, newOrder);
    }

    @PostMapping("/pay/{orderId}")
    public void payForOrder(@PathVariable Long orderId, @RequestParam String paymentUrl) {
        log.info("Received POST request to pay for order id={}", orderId);
        orderService.payForOrder(orderId, paymentUrl);
    }

    @PostMapping("/sendToNotification/{id}")
    public void sendToNotification(@PathVariable Long id) {
        orderService.sendNewOrder(id, "newOrderToNotification");
    }
}
