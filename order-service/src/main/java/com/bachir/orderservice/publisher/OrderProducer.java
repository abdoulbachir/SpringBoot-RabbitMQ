package com.bachir.orderservice.publisher;

import com.bachir.orderservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.order.routing.key}")
    private String orderRoutingKey;

    @Value("${rabbitmq.email.routing.key}")
    private String emailRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(OrderEvent orderEvent){
        //Sending order event to order queue
        log.info(String.format("Order event sent to Order Queue-> %s", orderEvent));
        rabbitTemplate.convertAndSend(exchange, orderRoutingKey, orderEvent);

        //Sending order event to email queue
        log.info(String.format("Order event sent to Email Queue -> %s", orderEvent));
        rabbitTemplate.convertAndSend(exchange, emailRoutingKey, orderEvent);
    }


}
