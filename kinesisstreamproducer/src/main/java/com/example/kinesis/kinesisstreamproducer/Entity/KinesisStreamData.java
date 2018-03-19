package com.example.kinesis.kinesisstreamproducer.Entity;


import lombok.Data;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public @Data class KinesisStreamData {

    @Value("${streamName}")
    private String streamName;
    @Value("${partitionKey}")
    private String partitionKey;
    private String jsonPayload;

}