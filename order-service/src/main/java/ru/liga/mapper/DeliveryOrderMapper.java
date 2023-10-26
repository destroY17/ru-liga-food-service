package ru.liga.mapper;

import org.springframework.stereotype.Component;
import ru.liga.dto.DeliveryOrderDto;
import ru.liga.model.Order;

@Component
public class DeliveryOrderMapper {
    public DeliveryOrderDto toDto(Order entity) {
        return new DeliveryOrderDto(
                entity.getId(),
                "testUrl",
                entity.getTimestamp().toLocalDateTime()
        );
    }
}
