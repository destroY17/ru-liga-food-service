package ru.liga.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.exception.PaymentException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {
    //Imitation storage of payments
    private final Map<UUID, String> payments = new HashMap<>();

    public void payForOrder(UUID orderId, String paymentUrl) {
        if (payments.containsKey(orderId)) {
            throw new PaymentException("Order id=" + orderId + "already paid for");
        }
        payments.put(orderId, paymentUrl);
        log.info("Order id={} is paid", orderId);
    }

    public void refundOfFunds(UUID orderId) {
        if (!payments.containsKey(orderId)) {
            throw new PaymentException("Order id=" + orderId + " was not paid");
        }

        payments.remove(orderId);
        log.info("A refund was issued for order id={}", orderId);
    }
}
