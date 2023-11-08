package ru.liga.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.liga.queue.KitchenQueue.*;

@Configuration
public class RoutingConfiguration {

    @Bean
    public Declarables kitchenToNotification() {
        Queue kitchenAcceptToNotification = new Queue(KITCHEN_ACCEPT, false);
        Queue kitchenDeniedToNotification = new Queue(KITCHEN_DENIED, false);
        Queue kitchenCompleteToNotification = new Queue(KITCHEN_COMPLETE, false);

        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(
                kitchenAcceptToNotification,
                kitchenDeniedToNotification,
                kitchenCompleteToNotification,
                directExchange,
                BindingBuilder.bind(kitchenAcceptToNotification).to(directExchange).with(KITCHEN_ACCEPT),
                BindingBuilder.bind(kitchenDeniedToNotification).to(directExchange).with(KITCHEN_DENIED),
                BindingBuilder.bind(kitchenCompleteToNotification).to(directExchange).with(KITCHEN_COMPLETE)
        );
    }
}
