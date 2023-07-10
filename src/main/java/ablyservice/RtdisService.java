package ablyservice;

//import ablyservice.domain.TopicEntry;
//import ablyservice.repository.BBCRepository;
import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.realtime.Channel;
import io.ably.lib.types.AblyException;
import io.ably.lib.types.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import io.ably.lib.realtime.Channel.MessageListener;

@Service
public class RtdisService {

    private final Logger logger = LoggerFactory.getLogger(RtdisService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

//    @Autowired
//    BBCRepository BBCRepository;

    @Autowired
    public RtdisService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//    private String getValidKafkaTopic(String channelName) {
//        return "rtdis_group_1" + channelName.replaceAll("[^A-Za-z0-9._-]", "_");
//    }

    public void startAblySubscription(String ablyApiKey, String channelName) {
        System.out.println("startAblySubscription method called");
        try {
            AblyRealtime ablyRealtime = new AblyRealtime(ablyApiKey);
            Channel channel = ablyRealtime.channels.get(channelName);

            channel.subscribe(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    // Log received message
                    logger.info("Received message from Ably: {}", message.data.toString());
                    System.out.println("Received message from Ably: " + message.data.toString());

                    // Publish to Kafka
                    publishToKafka(channelName, message.data.toString());
                }
            });
        } catch (AblyException e) {
            logger.error("An error occurred while subscribing to Ably channel: {}", e.getMessage());
        }
    }

    private void publishToKafka(String channelName, String jsonData) {
        // Publish the received JSON data to Kafka with the unique channel name as the topic

        //String kafkaTopic = getValidKafkaTopic(channelName);
        String kafkaTopic = "rtdis_1" ;
        kafkaTemplate.send(kafkaTopic, jsonData);

        System.out.println("kafkaTopic: " + kafkaTopic);
        System.out.println("jsonData: " + jsonData);

        // Log Kafka publishing
        logger.info("Published message to Kafka topic: {}, Message: {}", kafkaTopic, jsonData);
        System.out.println("Published message to Kafka topic: " + kafkaTopic + ", Message: " + jsonData);

        // Save to MongoDB
//        TopicEntry entity = new TopicEntry();
//        entity.setField1(jsonData);
//        BBCRepository.save(entity);

        // Log MongoDB saving
        logger.info("Saved message to MongoDB: {}", jsonData);
        System.out.println("Saved message to MongoDB: " + jsonData);
    }
}
