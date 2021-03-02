package com.streams.kafkastreamsexample;

import com.streams.kafkastreamsexample.pojo.Posts;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.annotation.Retry;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.vavr.collection.Array;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class CircuitController {

    String url = "https://jsonplaceholder.typicode.com/posts/1";
    static final String TESTER = "tester";

    @Autowired
    RetryRegistry retryRegistry;

    @CircuitBreaker(name = TESTER)
    @Retry(name= TESTER, fallbackMethod = "fallback")
    @GetMapping("/circuit")
    public Mono<Posts> circuit() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();

        Mono<Posts> postsMono = webClient.get()
                .uri("/x/1")
                .retrieve()
                .bodyToMono(Posts.class);

//        postsMono.subscribe(response -> log.info("Response body: {}", response.getBody()));
        return postsMono;
    }

    public Mono<Posts> fallback(Exception e) {
        log.error("Using fallback method after retries...");
        return Mono.just(new Posts());
    }
}
