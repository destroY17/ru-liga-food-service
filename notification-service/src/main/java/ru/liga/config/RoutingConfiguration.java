package ru.liga.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfiguration {
    @Bean
    public Declarables newOrderToKitchenQueue() {
        String orderToKitchenQueue = "newOrderToKitchen";
        String kitchenToOrderQueue = "kitchenToOrder";
        String orderToDeliveryQueue = "orderToDelivery";

        Queue newOrderToKitchen = new Queue(orderToKitchenQueue, false);
        Queue kitchenToOrder = new Queue(kitchenToOrderQueue, false);
        Queue orderToDelivery = new Queue(orderToDeliveryQueue, false);

        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(newOrderToKitchen,
                kitchenToOrder,
                orderToDelivery,
                directExchange,
                BindingBuilder.bind(newOrderToKitchen).to(directExchange).with(orderToDeliveryQueue),
                BindingBuilder.bind(kitchenToOrder).to(directExchange).with(kitchenToOrderQueue),
                BindingBuilder.bind(orderToDelivery).to(directExchange).with(orderToDeliveryQueue)
        );
    }
}
