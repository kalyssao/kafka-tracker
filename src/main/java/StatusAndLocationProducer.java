import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class StatusAndLocationProducer {

    private final Producer<String, String> producer;
    final String outTopic;

    public StatusAndLocationProducer(final Producer<String, String> producer, final String topic){
        this.producer = producer;
        outTopic = topic;
    }

    // Given a status message we produce a record
    public Future<RecordMetadata> produce(final String message) {
        final String key = "123";
        final String value;

        // parse the key and

        final ProducerRecord<String, String> producerRecord = new ProducerRecord<>(outTopic, key);

        return producer.send(producerRecord);
    }

    public static void main(String[] args) {
        final Producer<String, String> producer = new StatusAndLocationProducer();

        String filePath = "";

        try {
            List<String> linesToProduce = Files.readAllLines(Paths.get(filePath));
            List<Future<RecordMetadata>> metadata = linesToProduce.stream()
                    .map(producer::produce)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.err.printf("Error reading file %s due to %s %n", filePath, e);
        }
    }
}
