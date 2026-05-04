package org.dreamcommerce.dreamcommerce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class DreamCommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamCommerceApplication.class, args);
    }

    @KafkaListener(topics = "my-topic")
    public void listen(String message) {
        log.info("Received message: {}", message);
    }
}
