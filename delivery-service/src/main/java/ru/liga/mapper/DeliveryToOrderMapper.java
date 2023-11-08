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
    private final CustomerDeliveryMapper customerDeliveryMapper;

    public DeliveryDto toDto(Order entity) {
        return new DeliveryDto(
                entity.getId(),
                restaurantDeliveryMapper.toDto(entity.getRestaurant(), entity.getCourier()),
                customerDeliveryMapper.toDto(entity.getCustomer(), entity.getCourier()),
                calculatePayment(entity.getOrderItems())
        );
    }

    private double calculatePayment(List<OrderItem> orderItems) {
        double payment = 0;

        for (OrderItem item : orderItems) {
            payment += item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).doubleValue();
        }
        return payment;
    }

    public List<DeliveryDto> toDto(List<Order> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
