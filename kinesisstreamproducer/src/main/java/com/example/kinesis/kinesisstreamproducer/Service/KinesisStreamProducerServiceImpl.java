package com.example.kinesis.kinesisstreamproducer.Service;

import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.example.kinesis.kinesisstreamproducer.Entity.KinesisStreamData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class KinesisStreamProducerServiceImpl implements KinesisStreamProducerService{

    private static final Logger log = LoggerFactory.getLogger(KinesisStreamProducerServiceImpl.class);

    @Override
    public String pushToKinesisStream(KinesisStreamData kinesisStreamData) {
        KinesisProducerConfiguration config = KinesisProducerConfiguration.fromPropertiesFile("/Users/shebbar1/code/kinesis-sample/kinesisstreamproducer/src/main/resources/kinesisStream.properties");
        KinesisProducer kinesis = new KinesisProducer(config);
        ByteBuffer entity = null;
        try {
            entity = ByteBuffer.wrap(kinesisStreamData.getJsonPayload().toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Future<UserRecordResult> f = kinesis.addUserRecord(kinesisStreamData.getStreamName(), kinesisStreamData.getPartitionKey(), entity);
        UserRecordResult result = null;
        try {
            result = f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(result.isSuccessful()){
            log.debug("Success");
            return "{shardID: "+result.getShardId()+", sequenceNumber: "+result.getSequenceNumber()+"}";
        }
        else{
            log.debug("Failed");
            return "Push failed";
        }
    }
}