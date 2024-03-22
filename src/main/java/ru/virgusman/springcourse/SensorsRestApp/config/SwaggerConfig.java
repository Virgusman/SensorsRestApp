package ru.virgusman.springcourse.SensorsRestApp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Контроль замеров с метеорологических сенсоров")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("virgusman@gmail.com")
                                                .name("Siyanko Sergei")
                                )
                );
    }
}
