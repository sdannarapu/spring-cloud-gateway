package com.springcloudgatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableHystrix
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route(p -> p
                        .path("/all")
                        .filters(f ->
                                f.addRequestHeader("x-rapidapi-host", "restcountries-v1.p.rapidapi.com")
                                        .addRequestHeader("x-rapidapi-key", "29442eee2amshf8759a1a1566d6dp1dd3a0jsn90412ee21e01")
                                        .hystrix(config -> config.setName("countries-service")
                                                .setFallbackUri("forward:/countriesfallback"))
                        )
                        .uri("https://restcountries-v1.p.rapidapi.com")
                )
                .route(p -> p
                        .path("/v1/joke")
                        .filters(f ->
                                f.addRequestHeader("x-rapidapi-host", "joke3.p.rapidapi.com")
                                        .addRequestHeader("x-rapidapi-key", "29442eee2amshf8759a1a1566d6dp1dd3a0jsn90412ee21e01")
                                        .hystrix(config -> config.setName("joke-service")
                                                .setFallbackUri("forward:/jokefallback"))
                        )
                        .uri("https://joke3.p.rapidapi.com")
                )
                .build();
    }
}
