package com.bachir.stockservice.consumer;

import com.bachir.stockservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = "${rabbitmq.order.queue.name}")
    public void consume(OrderEvent event){
        log.info(String.format("Order event received in stock service -> %s", event.toString()));

        // save order event data to database
    }
}
