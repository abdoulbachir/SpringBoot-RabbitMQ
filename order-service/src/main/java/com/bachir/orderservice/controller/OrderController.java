package com.bachir.orderservice.controller;

import com.bachir.orderservice.dto.Order;
import com.bachir.orderservice.dto.OrderEvent;
import com.bachir.orderservice.publisher.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class OrderController {
    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        order.setOrderID(UUID.randomUUID().toString());

        OrderEvent event = new OrderEvent();
        event.setStatus("PENDING");
        event.setMessage("Order is pending");
        event.setOrder(order);

        orderProducer.sendMessage(event);

        return "Order sent to RabbitMQ ...";
    }
}
