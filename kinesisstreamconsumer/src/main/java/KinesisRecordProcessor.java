import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

public class KinesisRecordProcessor implements IRecordProcessor {

    private final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    public void initialize(String s) {
        System.out.print("Starting record processing for shardID: "+s);
    }

    public void processRecords(List<Record> records, IRecordProcessorCheckpointer iRecordProcessorCheckpointer) {
        for (Record record : records) {
            String data = null;
            try {
                data = decoder.decode(record.getData()).toString();
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            }
            System.out.print(record.getSequenceNumber() + ", " + record.getPartitionKey() + ", " + data + ", Created "
                    + record.getApproximateArrivalTimestamp() + " milliseconds ago.");

            //Get more information about payload by calling external service

            //Write data to ElasticSearch
            postToElasticSearch(data);
        }
    }

    public void shutdown(IRecordProcessorCheckpointer iRecordProcessorCheckpointer, ShutdownReason shutdownReason) {
        System.out.print("Stopped because of "+shutdownReason.toString());
    }

    public void postToElasticSearch(String data){
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //Month Year
        String index = "sampleindex";
        //Wasabi account id
        String type = "sample";

        IndexResponse response = client.prepareIndex(index, type)
                .setSource(data, XContentType.JSON)
                .setPipeline("geoip")
                .get();
    }

}
