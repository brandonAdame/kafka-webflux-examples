package com.streams.kafkastreamsexample.consumer;


import com.streams.kafkastreamsexample.pojo.Posts;
import com.streams.kafkastreamsexample.service.CircuitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
@Component
public class StreamsConsumer {

    private CircuitService circuitService;

    public StreamsConsumer(CircuitService circuitService) {
        this.circuitService = circuitService;
    }

    @Bean
    public Consumer<KStream<String, String>> process() {

        return input ->
                input.foreach((key, value) -> {
                    log.info("INCOMING --> Key: {}, Value: {}", key, value);
                    Mono<Posts> postsMono = circuitService.posts();

                    log.info("Before subscribing to request");
                    postsMono.subscribe();
                });
    }
}
