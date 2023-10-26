package ru.liga.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.dto.DeliveryDto;
import ru.liga.model.Order;
import ru.liga.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DeliveryToOrderMapper {
    private final RestaurantDeliveryMapper restaurantDeliveryMapper;
    private final CustomerMapper customerMapper;

    public DeliveryDto toDto(Order entity) {
        double payment = 0;

        for (OrderItem item : entity.getOrderItems()) {
            payment += item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).doubleValue();
        }

        return new DeliveryDto(
                entity.getId(),
                restaurantDeliveryMapper.toDto(entity.getRestaurant()),
                customerMapper.toDto(entity.getCustomer()),
                payment
        );
    }

    public List<DeliveryDto> toDto(List<Order> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
