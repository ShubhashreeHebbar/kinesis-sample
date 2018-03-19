package com.example.kinesis.kinesisstreamproducer.Entity;

import lombok.Data;
import org.json.JSONObject;

import java.util.Map;

public @Data class Payload {
    private String type;
    private String name;

    private String account;
    private String experiment;

    private Map<String, String > headers;



}