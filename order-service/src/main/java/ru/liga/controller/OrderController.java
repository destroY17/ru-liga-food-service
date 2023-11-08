package ru.liga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.liga.aop.annotation.UserLog;
import ru.liga.dto.*;
import ru.liga.model.OrderStatus;
import ru.liga.service.OrderService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Контроллер для работы с заказами
 */
@RestController
@RequestMapping("order-service/orders")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API работы с заказами")
public class OrderController {
    private final OrderService orderService;

    /**
     * Поиск всех заказов(по статусу - опционально) постранично
     * @param pageable настройка страниц
     * @param status статус заказа (необязательный параметр)
     * @return список заказов с пагинацией
     */
    @UserLog
    @Operation(summary = "Найти все/по статусу постранично")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Not authorized")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping
    public Page<OrderInfo> findOrdersByStatus(@PageableDefault Pageable pageable,
                                              @RequestParam(required = false) OrderStatus status) {
        log.info("Received GET request to find orders by status={}", status);
        return Optional.ofNullable(status)
                .map(s -> orderService.findOrdersByStatus(pageable, s))
                .orElse(orderService.findAllOrders(pageable));
    }

    /**
     * Поиск заказов по идентификатору
     * @param id идентификатор заказа
     * @return заказ с заданным идентификатором
     */
    @UserLog
    @Operation(summary = "Найти по идентификатору")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Not authorized")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{id}")
    public OrderInfo findOrderById(@PathVariable Long id) {
        log.info("Received GET request to find order by id={}", id);
        return orderService.findOrderById(id);
    }

    /**
     * Добавление нового заказа
     *
     * @param customerId идентификатор заказчика
     * @param newOrder информация о новом заказе
     * @return сведения о сделанном заказе
     */
    @UserLog
    @Operation(summary = "Добавить")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Not authorized")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping
    public DeliveryOrderDto addOrder(@RequestParam Long customerId, @Valid @RequestBody NewOrderDto newOrder) {
        log.info("Received POST request to add new order:{} by customer (id={})",
                newOrder.toString(), customerId);
        return orderService.addOrder(customerId, newOrder);
    }

    /**
     * Оплата заказа
     *
     * @param orderId идентификатор заказа
     * @param paymentUrl url для оплаты заказа
     */
    @UserLog
    @Operation(summary = "Оплатить")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Not authorized")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/pay/{orderId}")
    public void payForOrder(@PathVariable Long orderId, @RequestParam String paymentUrl) {
        log.info("Received POST request to pay for order id={}", orderId);
        orderService.payForOrder(orderId, paymentUrl);
    }

    /**
     * Обновление статуса заказа
     *
     * @param orderActionDto данные для обновления статуса
     */
    @UserLog
    @Operation(summary = "Обновить статус")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Not authorized")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/update")
    public void updateOrderStatus(@Valid @RequestBody OrderActionDto orderActionDto) {
        log.info("Received POST request to update status of order id={}, updated status={}",
                orderActionDto.getOrderId(), orderActionDto.getStatus());
        orderService.updateOrderStatus(orderActionDto);
    }

}
