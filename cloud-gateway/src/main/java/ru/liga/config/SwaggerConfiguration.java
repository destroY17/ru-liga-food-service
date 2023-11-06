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
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes("spring_oauth", new SecurityScheme()
//                                .type(SecurityScheme.Type.OAUTH2)
//                                .flows(new OAuthFlows()
//                                        .clientCredentials(new OAuthFlow()
//                                                .tokenUrl("http://localhost:9000/oauth/token")
//                                                .scopes(new Scopes()
//                                                        .addString("read", "for read operations")
//                                                        .addString("write", "for write operations")
//                                                )
//                                        )
//                                )
//                        )
//                )
//                .security(Collections.singletonList(
//                        new SecurityRequirement().addList("spring_oauth")))
//                .info(new Info()
//                        .title("Food Service API")
//                        .description("Сервис работы с заказами")
//                        .contact(new Contact().email("test@test.com").name("Andrei Eikhe"))
//                        .version("1.0")
//                );
//    }
}
