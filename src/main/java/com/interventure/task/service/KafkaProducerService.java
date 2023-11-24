
package com.interventure.task.service;

import com.interventure.task.message.Message;
import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;

/**
 *
 * @author bojana
 */
public interface KafkaProducerService {
    public void  send(Message product);
    public CompletableFuture<SendResult<String, Message>> sendAndReturnFuture(Message product);
}
