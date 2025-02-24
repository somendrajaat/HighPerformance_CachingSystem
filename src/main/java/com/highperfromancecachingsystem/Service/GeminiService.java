package com.highperfromancecachingsystem.Service;

import com.highperfromancecachingsystem.Model.GeminiRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import static com.highperfromancecachingsystem.Configuration.RabbitMQConfig.QUEUE_NAME;

@Service
public class GeminiService {

    private final RedisTemplate<String, String> redisTemplate;
    @Autowired
    private QueryProducer queryProducer;

    @Autowired
    public GeminiService(RedisTemplate<String, String> redisTemplate, QueryProducer queryProducer) {
        this.redisTemplate = redisTemplate;
        this.queryProducer = queryProducer;
    }

    public String generateText(String prompt) {
        // Check if response exists in Redis
        String cachedResponse = redisTemplate.opsForValue().get(prompt);
        if (cachedResponse != null) {
            return cachedResponse;
        }
        return queryProducer.sendQuery(prompt);
    }
}
