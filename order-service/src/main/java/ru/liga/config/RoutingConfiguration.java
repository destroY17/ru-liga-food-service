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
    public Declarables orderToNotificationQueue() {
        Queue orderToNotification = new Queue("newOrderToNotification", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(orderToNotification, directExchange,
                BindingBuilder.bind(orderToNotification).to(directExchange).with("newOrderToNotification"));
    }
}
