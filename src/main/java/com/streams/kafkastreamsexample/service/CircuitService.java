package com.streams.kafkastreamsexample.service;

import com.streams.kafkastreamsexample.pojo.Posts;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.circuitbreaker.springretry.SpringRetryCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

@Slf4j
@Service
public class CircuitService {

    @CircuitBreaker(name = "tester")
    public Mono<Posts> posts() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();

        log.info("Sending request to WebClient");
        Hooks.onErrorDropped(error -> {
            log.warn("An error happened... {}", error.getMessage());
        });
        return webClient.get()
                .uri("/x/1")
                .retrieve()
                .bodyToMono(Posts.class); // asynchronous call
    }
}
