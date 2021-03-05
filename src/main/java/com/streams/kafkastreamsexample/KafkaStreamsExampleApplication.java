package com.streams.kafkastreamsexample;

import com.streams.kafkastreamsexample.pojo.Posts;
import com.streams.kafkastreamsexample.service.CircuitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
@Slf4j
public class KafkaStreamsExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsExampleApplication.class, args);
    }

//    @Bean
//    public Function<String, String> send() {
//        return input -> {
//            System.out.println("Uppercasing: " + input);
//            return input.toUpperCase();
//        };
//    }

//    @Bean
//    public Function<KStream<Object, String>, KStream<Object, WordCount>> process() {
//
//        return input -> input
//                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
//                .map((key, value) -> new KeyValue<>(value, value))
//                .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
//                .windowedBy(SessionWindows.with(Duration.ofSeconds(30)))
//                .count()
//                .toStream()
//                .map((key, value) -> new KeyValue<>(null,
//                        new WordCount(key.key(), value, new Date(key.window().start()), new Date(key.window().end()))))
//                ;
//    }


//    @Bean
//    public Consumer<KStream<String, String>> process() {
//
//        return input ->
//                input.foreach((key, value) -> {
//                    System.out.println("Key: " + key + " Value: " + value);
//                    Mono<Posts> postsMono = circuitService.posts();
//
//                    log.info("Before subscribing to request");
//                    postsMono.subscribe();
//                });
//    }
}
