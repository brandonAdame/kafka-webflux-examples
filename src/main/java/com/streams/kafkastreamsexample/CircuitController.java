package com.streams.kafkastreamsexample;

import com.streams.kafkastreamsexample.pojo.Comments;
import com.streams.kafkastreamsexample.pojo.Posts;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyExtractor;
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

//    @Autowired
//    RetryRegistry retryRegistry;
//
//    @CircuitBreaker(name = TESTER)
//    @Retry(name = TESTER, fallbackMethod = "fallback")
    @GetMapping("/circuit")
    public Mono<Posts> circuit() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();

        return webClient.get()
                .uri("/x/1")
                .retrieve()
                .bodyToMono(Posts.class); // asynchronous call
    }

//    @CircuitBreaker(name = TESTER)
//    @Retry(name = TESTER, fallbackMethod = "fallback")
    @GetMapping("/post")
    public Posts posts() {
        return call();
    }


    public Posts call_retry() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();

//        io.github.resilience4j.retry.Retry retry = io.github.resilience4j.retry.Retry.ofDefaults("retry");
        return webClient.get()
                .uri("/post/1")
                .retrieve()
                .bodyToMono(Posts.class)
//                .transformDeferred(RetryOperator.of(retry))
                .block(); // synchronous call
    }

    // These annotations have no effect in this context
//    @CircuitBreaker(name = TESTER)
//    @Retry(name= TESTER, fallbackMethod = "fallback")
    public Posts call() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();

//        io.github.resilience4j.retry.Retry retry = io.github.resilience4j.retry.Retry.ofDefaults("tester");
        return webClient.get()
                .uri("/x/1")
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("An error occurred")))
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> Mono.error(new RuntimeException("There was a service error")))
                .bodyToMono(Posts.class)
                .retryWhen(reactor.util.retry.Retry.backoff(5, Duration.ofMillis(20))
                        .doAfterRetry(retrySignal -> log.warn("Retrying operation")))
                .onErrorResume(error -> Mono.just(new Posts()))
                .block(); // synchronous call
    }

    public Posts fallback(Exception e) {
        log.error("Using fallback method after retries...");
//        return Mono.just(new Posts());
        return new Posts();
    }
}
