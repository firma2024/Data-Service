package com.firma.data.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public springfox.documentation.spring.web.plugins.Docket api() {
        return new springfox.documentation.spring.web.plugins.Docket(
                springfox.documentation.spi.DocumentationType.SWAGGER_2)
                .select()
                .apis(
                        springfox.documentation.builders.RequestHandlerSelectors
                                .basePackage("com.firma.data.controller"))
                .build();
    }
}
