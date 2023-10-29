package ru.liga.util;

import lombok.experimental.UtilityClass;
import ru.liga.exception.IncorrectOrderStatusException;
import ru.liga.model.OrderStatus;

@UtilityClass
public class OrderUtil {
    public void correctStatusOrElseThrow(OrderStatus orderStatus, OrderStatus waitingStatus) {
        if (orderStatus != waitingStatus) {
            throw new IncorrectOrderStatusException("Order status=" + orderStatus
                    + " but waiting status=" + waitingStatus);
        }
    }
}
