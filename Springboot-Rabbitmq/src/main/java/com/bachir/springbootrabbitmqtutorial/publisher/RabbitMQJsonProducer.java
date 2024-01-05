package com.bachir.springbootrabbitmqtutorial.publisher;

import com.bachir.springbootrabbitmqtutorial.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQJsonProducer {

    // Need to fetch Exchange and Routing Key values from application.properties file
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.json.routing.key}")
    private String routingKey;


    private final RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(User user){
        rabbitTemplate.convertAndSend(exchange, routingKey, user);
        log.info(String.format("Json message sent -> %s", user));
    }
}
