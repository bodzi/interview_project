package com.interventure.task.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interventure.task.entitiy.Product;
import com.interventure.task.message.Message;
import com.interventure.task.message.Message.Action;
import com.interventure.task.service.KafkaProducerService;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.assertj.KafkaConditions.value;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author bojana
 */
@DirtiesContext
@SpringBootTest()
@ActiveProfiles("test")
@EmbeddedKafka(controlledShutdown = true)
@Slf4j
public class KafkaProducerServiceIntegrationTest {

    @Autowired
    private KafkaProducerService kafkaMessageProducerService;

    private KafkaMessageListenerContainer<String, Product> container;

    private BlockingQueue<ConsumerRecord<String, String>> consumerRecords;

    @Value("${app.kafka.producer.topic}")
    private String topic;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @BeforeEach
    public void setUp() {
        consumerRecords = new LinkedBlockingQueue<>();

        ContainerProperties containerProperties = new ContainerProperties(topic);

        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps(
                "sender", "false", embeddedKafka);

        DefaultKafkaConsumerFactory<String, Product> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);

        container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
        container.setupMessageListener((MessageListener<String, String>) record -> {
            log.debug("Listened message='{}'", record.toString());
            consumerRecords.add(record);
        });
        container.start();

        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
    }

    @AfterEach
    public void tearDown() {
        container.stop();
    }

    @Test
    public void send_created_product_event() throws InterruptedException, JsonProcessingException {
        Message event = new Message(Action.CREATED, Product.builder().name("Product name").price(99.9).build());

        kafkaMessageProducerService.send(event);

        ConsumerRecord<String, String> received = consumerRecords.poll(10, TimeUnit.SECONDS);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(event);

        assertThat(received).has(value(json));
        assertThat(received).has(key(null));
    }

}
