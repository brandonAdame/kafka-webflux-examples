package com.streams.kafkastreamsexample;

//import org.springframework.cloud.circuitbreaker.springretry.SpringRetryCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.springretry.SpringRetryConfigBuilder;
//import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

public class AppConfig {

//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
//                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
//    }

//    @Bean
//    public Customizer<SpringRetryCircuitBreakerFactory> defaultRetryCustomizer() {
//        return factory -> factory.configureDefault(id -> new SpringRetryConfigBuilder(id)
//                .retryPolicy(new AlwaysRetryPolicy()).build());
//    }
//
//    @Bean
//    public Customizer<SpringRetryCircuitBreakerFactory> slowCustomizer() {
//        return factory -> factory.configure(builder -> builder.retryPolicy(new AlwaysRetryPolicy()).build(), "service");
//    }
//
//    public Customizer<RetryTemplate> retryTemplateCustomizer() {
//        return retryTemplate -> new RetryTemplateBuilder().customPolicy(new AlwaysRetryPolicy()).build();
//    }
//
//    @Bean
//    public SpringRetryCircuitBreakerFactory springRetryCircuitBreakerFactory() {
//        SpringRetryCircuitBreakerFactory factory = new SpringRetryCircuitBreakerFactory();
//        factory.addRetryTemplateCustomizers(retryTemplateCustomizer(), "service");
//        return factory;
//    }
}
