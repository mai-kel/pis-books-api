package com.example.bookapi.kafka;

import com.example.bookapi.dtos.BookDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.example.bookapi.entities.Book;

import java.util.Map;

@Service
public class KafkaProducer {

    private static final String TOPIC = "books-events";
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(String method, Long id) {
        LOGGER.info(String.format("Message sent -> method: %s, id: %d", method, id));
        Map<String, Object> payload = Map.of(
                "method", method,
                "id", id
        );
        kafkaTemplate.send(TOPIC, payload);
    }
}
