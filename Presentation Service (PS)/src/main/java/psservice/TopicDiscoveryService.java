package psservice;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
public class TopicDiscoveryService {

    private final AdminClient adminClient;

    @Autowired
    public TopicDiscoveryService(KafkaAdmin kafkaAdmin) {
        Properties props = new Properties();
        props.putAll(kafkaAdmin.getConfigurationProperties());
        adminClient = AdminClient.create(props);
    }

    public List<String> getAllDataElementTopics() throws ExecutionException, InterruptedException {
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true); // Set to true if you also want to include internal topics

        ListTopicsResult topicsResult = adminClient.listTopics(options);
        KafkaFuture<java.util.Map<String, TopicListing>> topicsFuture = topicsResult.namesToListings();

        List<String> topics = new ArrayList<>();

        for (TopicListing topicListing : topicsFuture.get().values()) {
            String topicName = topicListing.name();
            if (topicName.startsWith("DS_")) {
                topics.add(topicName);
            }
        }

        return topics;
    }

    public void close() {
        adminClient.close();
    }
}
