package com.interventure.task.service.impl;

import com.interventure.task.message.Message;
import com.interventure.task.service.KafkaProducerService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

/**
 *
 * @author bojana
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, Message> kafkaProducer;

    @Value("${app.kafka.producer.topic}")
    private String topic;

    @Override
    public void send(Message msg) {

        CompletableFuture<SendResult<String, Message>> future = kafkaProducer.send(topic, msg);
        future.whenComplete((result, ex) -> {

            if (ex == null) {
                log.info("Succefuly sent message {} with offset [{}] to topic {}", msg, result.getRecordMetadata().offset(), topic);

            } else {
                log.error("Failed to sent message {} to topic {}. Exception {}", msg, topic, ex.getMessage());

            }

        });

    }

    @Override
    public CompletableFuture<SendResult<String, Message>> sendAndReturnFuture(Message msg) {

        return kafkaProducer.send(topic, msg);

    }
}
