package ru.liga.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.liga.queue.DeliveryQueue.*;

@Configuration
public class RoutingConfiguration {
    @Bean
    public Declarables deliveryToNotificationQueue() {
        Queue deliveryTaken = new Queue(DELIVERY_TAKEN, false);
        Queue deliveryComplete = new Queue(DELIVERY_COMPLETE, false);
        Queue notifyCouriers = new Queue(NOTIFY_COURIERS, false);

        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(
                deliveryTaken, deliveryComplete, notifyCouriers,
                directExchange,
                BindingBuilder.bind(deliveryTaken).to(directExchange).with(DELIVERY_TAKEN),
                BindingBuilder.bind(deliveryComplete).to(directExchange).with(DELIVERY_COMPLETE),
                BindingBuilder.bind(notifyCouriers).to(directExchange).with(NOTIFY_COURIERS)
        );
    }
}
