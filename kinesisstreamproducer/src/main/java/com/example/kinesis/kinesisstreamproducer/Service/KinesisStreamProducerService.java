package com.example.kinesis.kinesisstreamproducer.Service;

import com.example.kinesis.kinesisstreamproducer.Entity.KinesisStreamData;
import org.springframework.stereotype.Service;


@Service
public interface KinesisStreamProducerService {


    String pushToKinesisStream(KinesisStreamData kinesisStreamData);
}