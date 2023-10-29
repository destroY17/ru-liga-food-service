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
        Queue newOrderToKitchen = new Queue("newOrderToKitchen", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(newOrderToKitchen, directExchange,
                BindingBuilder.bind(newOrderToKitchen).to(directExchange).with("newOrderToKitchen"));
    }

    @Bean
    public Declarables orderToDeliveryQueue() {
        Queue orderToDelivery = new Queue("orderToDelivery", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(orderToDelivery, directExchange,
                BindingBuilder.bind(orderToDelivery).to(directExchange).with("orderToDelivery"));
    }

}
