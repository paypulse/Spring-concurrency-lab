package com.example.springconcurrencylab.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public OpenAPI SpringConcurrencyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Concurrency")
                        .description("Spring Concurrency Lab API")
                        .version("v1.0.0"));
    }
}
