package ru.liga.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.dto.OrderActionDto;

@FeignClient(name = "delivery-client", url = "localhost:8082/kitchen")
public interface DeliveryClient {
    @PostMapping("/update")
    void updateOrderStatus(@RequestBody OrderActionDto orderActionDto);
}
