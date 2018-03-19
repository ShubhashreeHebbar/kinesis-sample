package com.example.kinesis.kinesisstreamproducer.Controller;


import com.example.kinesis.kinesisstreamproducer.Entity.KinesisStreamData;
import com.example.kinesis.kinesisstreamproducer.Entity.Payload;
import com.example.kinesis.kinesisstreamproducer.Service.KinesisStreamProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class KinesisStreamProducerController {

    @Autowired
    KinesisStreamProducerService service;

    @Autowired
    KinesisStreamData entity;

    @RequestMapping(value="/send", method=POST)
    public String pushToKinesisStream(@RequestHeader Map<String,String> headers, @RequestBody Payload payload) throws JsonProcessingException {

        payload.setHeaders(headers);
        ObjectMapper objectMapper = new ObjectMapper();
        entity.setJsonPayload(objectMapper.writeValueAsString(payload));
        return service.pushToKinesisStream(entity);

    }
}