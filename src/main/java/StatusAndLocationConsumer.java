import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class StatusAndLocationConsumer {
    private final Consumer<String, String> consumer;
    private final ConsumerRecordsHandler recordsHandler;

    public StatusAndLocationConsumer(final Consumer<String, String> consumer,
                                     final ConsumerRecordsHandler<String, String> recordsHandler) {
        this.consumer = consumer;
        this.recordsHandler = recordsHandler;
    }

    public static Properties loadProperties(String filename) throws IOException {
        final Properties props = new Properties();
        final FileInputStream input = new FileInputStream(filename);

        props.load(input);
        input.close();

        return props;
    }

    public void runConsume(final Properties consumerProperties) {
        final String filePath = consumerProperties.getProperty("input.topic.name");
        consumer.subscribe(Collections.singletonList("statusAndLocations"));
        try {
            while (true) {
                ConsumerRecords records = consumer.poll(Duration.ofSeconds(1));
                recordsHandler.process(records);
            }
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) throws Exception {
        final Properties properties = StatusAndLocationConsumer.loadProperties("configuration/consumer.properties");
        final Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        final FileWritingRecordsHandler recordsHandler = new FileWritingRecordsHandler(Paths.get(properties.getProperty("file.path")));
        final StatusAndLocationConsumer statusAndLocationConsumer = new StatusAndLocationConsumer(consumer, recordsHandler);

        statusAndLocationConsumer.runConsume(properties);
    }
}
