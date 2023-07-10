package psservice;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class PresentationService {
    private static final Logger logger = LoggerFactory.getLogger(PresentationService.class);
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String[] DATA_TOPICS = {"DS_date", "DS_description", "DS_link", "DS_title", "DS_topic"};
    private static final String CSV_FILE_PATH = "C:/Users/yihun/Desktop/SA/Project 2023/data.csv";

    public static void main(String[] args) {
        Properties consumerProps = createConsumerProperties();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps)) {
            consumer.subscribe(Collections.singleton("DS"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                if (!records.isEmpty()) {
                    writeRecordsToCSV(records);
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred in Presentation Service", e);
        }
    }

    private static void writeRecordsToCSV(ConsumerRecords<String, String> records) {
        try (CSVPrinter csvPrinter = CSVFormat.DEFAULT.print(new FileWriter(CSV_FILE_PATH, true))) {
            for (ConsumerRecord<String, String> record : records) {
                String topic = record.topic();
                if (isDataTopic(topic)) {
                    csvPrinter.print(record.timestamp()); // Write timestamp in the first column
                    csvPrinter.print(record.value()); // Write value in the next column
                    csvPrinter.println(); // Move to the next row
                }
            }
            logger.info("Records successfully written to CSV file: {}", CSV_FILE_PATH);
        } catch (IOException e) {
            logger.error("Error occurred while writing records to CSV", e);
        }
    }

    private static boolean isDataTopic(String topic) {
        for (String dataTopic : DATA_TOPICS) {
            if (dataTopic.equals(topic)) {
                return true;
            }
        }
        return false;
    }

    private static Properties createConsumerProperties() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "presentation-service");
        return props;
    }
}
