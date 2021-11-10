import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.Consumer;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;

public class StatusAndLocationConsumer {

    private final Consumer<String, String> consumer;


    public StatusAndLocationConsumer(final Consumer<String, String> consumer,
                                     final ConsumerRecordsHandler<String, String> recordsHandler) {
        this.consumer = consumer;
    }

    public static void main(String[] args) {
        final Consumer<String, String> consumer = new StatusAndLocationConsumer();
        consumer.subscribe(Collections.singletonList("statusAndLocations"));

        final String filePath = "";

        try {

            while (true) {
                ConsumerRecords records = consumer.poll(Duration.ofSeconds(1));
                FileWritingRecordsHandler handler = new FileWritingRecordsHandler(Paths.get(filePath));

                handler.process(records);
            }
        } finally {
            consumer.close();
        }
    }
}
