package jdrsservice;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicListing;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class TopicDiscoveryService {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static boolean topicExists(String topicName) {
        Properties props = new Properties();
        props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        try (AdminClient adminClient = AdminClient.create(props)) {
            ListTopicsOptions options = new ListTopicsOptions();
            options.listInternal(true);

            ListTopicsResult topicsResult = adminClient.listTopics(options);
            Set<String> topicNames = topicsResult.names().get();

            return topicNames.contains(topicName);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Collection<TopicListing> getAllTopics() {
        Properties props = new Properties();
        props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        try (AdminClient adminClient = AdminClient.create(props)) {
            ListTopicsOptions options = new ListTopicsOptions();
            options.listInternal(true);

            ListTopicsResult topicsResult = adminClient.listTopics(options);
            return topicsResult.listings().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
