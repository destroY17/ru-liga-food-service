package ru.liga.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.liga.queue.NotifyQueue.*;

@Configuration
public class RoutingConfiguration {
    @Bean
    public Declarables newOrderToKitchenQueue() {
        Queue newOrderToKitchen = new Queue(ORDER_SERVICE_TO_KITCHEN, false);
        Queue kitchenToOrder = new Queue(KITCHEN_TO_ORDER_SERVICE, false);
        Queue orderToDelivery = new Queue(ORDER_SERVICE_TO_KITCHEN, false);
        Queue deliveryToOrder = new Queue(DELIVERY_TO_ORDER, false);

        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(
                newOrderToKitchen, kitchenToOrder, orderToDelivery, deliveryToOrder,
                directExchange,
                BindingBuilder.bind(newOrderToKitchen).to(directExchange).with(ORDER_SERVICE_TO_KITCHEN),
                BindingBuilder.bind(kitchenToOrder).to(directExchange).with(KITCHEN_TO_ORDER_SERVICE),
                BindingBuilder.bind(orderToDelivery).to(directExchange).with(ORDER_SERVICE_TO_KITCHEN),
                BindingBuilder.bind(deliveryToOrder).to(directExchange).with(DELIVERY_TO_ORDER)
        );
    }
}
