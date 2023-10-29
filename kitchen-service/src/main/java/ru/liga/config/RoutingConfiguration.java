package ru.liga.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfiguration {

    @Bean
    public Declarables kitchenToNotification() {
        Queue kitchenToNotification = new Queue("kitchenToNotification", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(kitchenToNotification, directExchange,
                BindingBuilder.bind(kitchenToNotification).to(directExchange).with("kitchenToNotification"));
    }
}
