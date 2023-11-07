package ru.liga.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfiguration {

    @Bean
    public Declarables kitchenToNotification() {
        String acceptQueue = "kitchenAcceptToNotification";
        String deniedQueue = "kitchenDeniedToNotification";
        String completeQueue = "kitchenCompleteToNotification";

        Queue kitchenAcceptToNotification = new Queue(acceptQueue, false);
        Queue kitchenDeniedToNotification = new Queue(deniedQueue, false);
        Queue kitchenCompleteToNotification = new Queue(completeQueue, false);

        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(
                kitchenAcceptToNotification,
                kitchenDeniedToNotification,
                kitchenCompleteToNotification,
                directExchange,
                BindingBuilder.bind(kitchenAcceptToNotification).to(directExchange).with(acceptQueue),
                BindingBuilder.bind(kitchenDeniedToNotification).to(directExchange).with(deniedQueue),
                BindingBuilder.bind(kitchenCompleteToNotification).to(directExchange).with(completeQueue)
        );
    }
}
