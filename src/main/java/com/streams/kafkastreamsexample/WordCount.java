package com.streams.kafkastreamsexample;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class WordCount {
    String word;
    long count;
    Date start;
    Date end;
}
