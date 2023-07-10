package jdrsservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JDRSService {
    private static final String OUTPUT_TOPIC_PREFIX = "DS_";

    private static final Logger logger = LoggerFactory.getLogger(JDRSService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "RTDS")
    public void processJsonMessage(ConsumerRecord<String, String> record) {
        String json = record.value();

        try {
            processJson(json);
        } catch (Exception e) {
            logger.error("Error processing JSON: " + e.getMessage());
        }
    }

    private void processJson(String json) throws Exception {
        JsonNode rootNode = objectMapper.readTree(json);

        // Extract the desired data elements
        String title = rootNode.get("title").asText();
        String description = rootNode.get("description").asText();
        String date = rootNode.get("date").asText();
        String link = rootNode.get("link").asText();

        // Publish the extracted data elements to their corresponding topics
        publishDataElement("title", title);
        publishDataElement("description", description);
        publishDataElement("date", date);
        publishDataElement("link", link);
    }

    private void publishDataElement(String name, String value) {
        String topic = OUTPUT_TOPIC_PREFIX + name;
        String logMessage = String.format("Publishing message to Kafka topic: %s: %s", topic, value);

        // Log Kafka publishing
        logger.info(logMessage);
        System.out.println(logMessage);

        kafkaTemplate.send(topic, name, value);
    }



}
