import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class StatusAndLocationProducer {
    private final Producer<String, String> producer;
    final String outTopic;

    public StatusAndLocationProducer(final Producer<String, String> producer, final String topic){
        this.producer = producer;
        outTopic = topic;
    }

    public static Properties loadProperties(String filename) throws IOException {
        final Properties props = new Properties();
        final FileInputStream input = new FileInputStream(filename);

        props.load(input);
        input.close();

        return props;
    }

    // Given a status message we produce a record
    public Future<RecordMetadata> produce(final String message) {
        final String[] parts = message.split("-");
        final String key, value;
        if (parts.length > 1) {
            key = parts[0];
            value = parts[1];
        } else {
            key = "NO-KEY";
            value = parts[0];
        }
        final ProducerRecord<String, String> producerRecord = new ProducerRecord<>(outTopic, key, value);
        return producer.send(producerRecord);
    }

    // Print the metadata of the record
    public void printMetadata(final Collection<Future<RecordMetadata>> metadata,
                              final String fileName) {
        System.out.println("Offsets and timestamps committed in batch from " + fileName);
        metadata.forEach(m -> {
            try {
                final RecordMetadata recordMetadata = m.get();
                System.out.println("Record written to offset " + recordMetadata.offset() + " timestamp " + recordMetadata.timestamp());
            } catch (InterruptedException | ExecutionException e) {
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        final Properties properties = StatusAndLocationProducer.loadProperties("configuration/dev.properties");
        final Producer<String, String> producer = new KafkaProducer<>(properties);
        final StatusAndLocationProducer statusAndLocationProducer = new StatusAndLocationProducer(producer, properties.getProperty("output.topic.name"));

        String filePath = "input.txt";

        try {
            List<String> linesToProduce = Files.readAllLines(Paths.get(filePath));

            List<Future<RecordMetadata>> metadata = linesToProduce.stream()
                    .map(statusAndLocationProducer::produce)
                    .collect(Collectors.toList());

            statusAndLocationProducer.printMetadata(metadata, filePath);

        } catch (IOException e) {
            System.err.printf("Error reading file %s due to %s %n", filePath, e);
        }
        finally {
            producer.close();
        }
    }
}
