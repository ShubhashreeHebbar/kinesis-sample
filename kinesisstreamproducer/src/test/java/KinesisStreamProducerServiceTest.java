import com.example.kinesis.kinesisstreamproducer.Entity.KinesisStreamData;
import com.example.kinesis.kinesisstreamproducer.Entity.Payload;
import com.example.kinesis.kinesisstreamproducer.Service.KinesisStreamProducerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class KinesisStreamProducerServiceTest {

    @Autowired
    KinesisStreamProducerServiceImpl kinesisStreamProducerService;
    KinesisStreamData kinesisStreamData;



    @Before
    public void setup(){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type","application/json");
        Payload payload = new Payload();
        payload.setType("Dog");
        payload.setName("Tom");
        payload.setHeaders(headers);

        kinesisStreamData = new KinesisStreamData();
        kinesisStreamData.setJsonPayload(payload.toString());

    }

    @Test
    public void testPushToKinesisStream(){

        kinesisStreamProducerService.pushToKinesisStream(kinesisStreamData);


    }
}
