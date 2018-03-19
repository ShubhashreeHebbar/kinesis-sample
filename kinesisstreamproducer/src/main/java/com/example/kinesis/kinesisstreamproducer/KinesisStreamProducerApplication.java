package com.example.kinesis.kinesisstreamproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource("kinesisStream.properties")
public class KinesisStreamProducerApplication {

    public static void main(String[] args){

        SpringApplication.run(KinesisStreamProducerApplication.class, args);
    }
}
