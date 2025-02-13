package com.highperfromancecachingsystem.Service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class QueryConsumer {

    private final RedisService redisService;

    public QueryConsumer(RedisService redisService) {
        this.redisService = redisService;
    }

    @RabbitListener(queues = "queryQueue")
    public void receiveQuery(String query) {
        // Simulate AI generating a result
        String result = "Processed result for: " + query;
        redisService.saveToCache(query, result);
    }
}
