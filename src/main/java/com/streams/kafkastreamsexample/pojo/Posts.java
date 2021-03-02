package com.streams.kafkastreamsexample.pojo;

import lombok.Data;

@Data
public class Posts {
    int userId;
    int id;
    String title;
    String body;
}
