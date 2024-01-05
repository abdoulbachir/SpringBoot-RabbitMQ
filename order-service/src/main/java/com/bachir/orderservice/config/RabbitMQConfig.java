package com.bachir.orderservice.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.order.queue.name}")
    private String orderQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.order.routing.key}")
    private String orderRoutingKey;

    @Value("${rabbitmq.email.queue.name}")
    private String emailQueue;

    @Value("${rabbitmq.email.routing.key}")
    private String emailRoutingKey;

    // spring bean for order queue
    @Bean
    public Queue orderQueue(){
        return new Queue(orderQueue);
    }

    // spring bean for email queue
    @Bean
    public Queue emailQueue(){
        return new Queue(emailQueue);
    }

    //spring bean for exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    //spring bean for binding between exchange and order queue using order routing key
    @Bean
    public Binding orderBinding(){
        return BindingBuilder
                .bind(orderQueue())
                .to(exchange())
                .with(orderRoutingKey);
    }

    //spring bean for binding between exchange and email queue using order routing key
    @Bean
    public Binding emailBinding(){
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(emailRoutingKey);
    }

    // spring bean message converter
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    // spring bean for RabbitTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        //Set RabbitTemplate Object
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // Set the message converter for RabbitMQ
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
