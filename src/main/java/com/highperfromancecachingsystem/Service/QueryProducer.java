package com.highperfromancecachingsystem.Service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueryProducer {

    private final RabbitTemplate rabbitTemplate;

    public QueryProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendQuery(String query) {
        rabbitTemplate.convertAndSend("queryQueue", query);
    }
}
