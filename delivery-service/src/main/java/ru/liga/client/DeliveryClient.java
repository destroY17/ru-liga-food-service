package ru.liga.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.dto.OrderActionDto;

import javax.validation.Valid;

@FeignClient(name = "delivery-client", url = "localhost:8084/order-service")
public interface DeliveryClient {
    @PostMapping("/orders/update")
    void updateOrderStatus(@Valid @RequestBody OrderActionDto orderAction);
}
