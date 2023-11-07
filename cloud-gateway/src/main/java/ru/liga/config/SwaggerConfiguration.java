package ru.liga.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI gateWayOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Food service API")
                .description("Documentation for all the Microservices in Food service")
                .version("v1.0.0")
                .contact(new Contact().name("Andrei Eikhe").email("test@gmail.com")
                )
        );
    }
}
