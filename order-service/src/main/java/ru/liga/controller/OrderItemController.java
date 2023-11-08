package ru.liga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.liga.aop.annotation.UserLog;
import ru.liga.model.OrderItem;
import ru.liga.dto.NewOrderItemDto;
import ru.liga.service.OrderItemService;

import javax.validation.Valid;

/**
 * Контроллер для работы с товарами заказа
 */
@RestController
@RequestMapping("order-service/order-items")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Api работы с товарами заказа")
public class OrderItemController {
    private final OrderItemService orderItemService;

    /**
     * Добавление нового товара в заказ
     *
     * @param newOrderItemDto сведения о новом товаре
     * @return добавленный в заказ товар
     */
    @UserLog
    @Operation(summary = "Добавить")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Not authorized")
    @ApiResponse(responseCode = "404", description = "Order item not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping
    public OrderItem addOrderItem(@Valid @RequestBody NewOrderItemDto newOrderItemDto) {
        log.info("Received POST request to add new order item:{}", newOrderItemDto.toString());
        return orderItemService.addOrderItem(newOrderItemDto);
    }

    /**
     * Удаление товара из заказа
     *
     * @param id идентификатор товара
     */
    @Operation(summary = "Удалить по идентификатору")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Not authorized")
    @ApiResponse(responseCode = "404", description = "Order item not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{id}")
    public void deleteOrderItemById(@PathVariable Long id) {
        log.info("Received DELETE request to delete order item id={}", id);
        orderItemService.deleteOrderItemById(id);
    }
}