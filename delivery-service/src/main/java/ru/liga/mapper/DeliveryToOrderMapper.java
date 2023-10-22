package ru.liga.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.dto.DeliveryDto;
import ru.liga.dto.MenuItemInOrderDto;
import ru.liga.dto.OrderInfoDto;
import ru.liga.dto.RestaurantDeliveryDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DeliveryToOrderMapper {
    //TODO: change mapping logic
    public DeliveryDto mapToDelivery(OrderInfoDto order) {
        double payment = 0;
        for (MenuItemInOrderDto item : order.getItems()) {
            payment += item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).doubleValue();
        }

        return new DeliveryDto(
                order.getId(),
                new RestaurantDeliveryDto(order.getRestaurant().getAddress(),
                order.getCustomer().getDistance()),
                order.getCustomer(),
                payment
        );
    }

    public List<DeliveryDto> mapToDelivery(List<OrderInfoDto> orders) {
        return orders.stream()
                .map(this::mapToDelivery)
                .collect(Collectors.toList());
    }
}