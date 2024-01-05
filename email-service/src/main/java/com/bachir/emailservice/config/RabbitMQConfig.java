package com.bachir.emailservice.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

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
