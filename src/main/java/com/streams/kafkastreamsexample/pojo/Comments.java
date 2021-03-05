package com.streams.kafkastreamsexample.pojo;

import lombok.Data;

@Data
public class Comments {
    int postId;
    int id;
    String name;
    String email;
    String body;
}
