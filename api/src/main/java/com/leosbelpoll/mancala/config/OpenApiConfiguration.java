package com.leosbelpoll.mancala.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI(){
        Info info = new Info()
                .title("API Mancala game")
                .version("1.0.0");
        return new OpenAPI().info(info);
    }

}
