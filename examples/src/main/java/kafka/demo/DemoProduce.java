package kafka.demo;

import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @author chenpeibin
 * @version 1.0
 * @date 2020/4/28 16:49
 */
public class DemoProduce {
    public static final String brokerList = "192.168.0.2:9092,192.168.0.3:9092,192.168.0.4:9092";
    public static final String topic = "hidden-topic";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("client.id", "hidden-producer-client-id-1");
        properties.put("bootstrap.servers", brokerList);

        Producer<String,String> producer = new KafkaProducer<String,String>(properties);

        while (true) {
            String message = "kafka_message-" + System.currentTimeMillis() + "-edited by hidden.zhu";
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic,message);
            try {
                Future<RecordMetadata> future =  producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        System.out.print(metadata.offset()+"    ");
                        System.out.print(metadata.topic()+"    ");
                        System.out.println(metadata.partition());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
