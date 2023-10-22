package ru.liga.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.liga.dto.OrderInfoDto;
import ru.liga.model.OrderStatus;

@FeignClient(name = "order-service", url = "http://localhost:8084/orders")
public interface OrderFeign {
    @GetMapping
    Page<OrderInfoDto> findOrdersByStatus(@RequestParam OrderStatus status);
}