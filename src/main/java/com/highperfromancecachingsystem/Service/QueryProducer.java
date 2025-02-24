package com.highperfromancecachingsystem.Service;

import com.highperfromancecachingsystem.Model.GeminiRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueryProducer {

    private final RabbitTemplate rabbitTemplate;

    public QueryProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String sendQuery(String query) {
        return (String)rabbitTemplate.convertSendAndReceive("queryQueue", query);
    }
}
