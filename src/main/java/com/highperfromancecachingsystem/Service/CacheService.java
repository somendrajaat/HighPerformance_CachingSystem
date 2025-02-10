package com.highperfromancecachingsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    QueryProducer queryProducer;
    @Autowired
    RedisService redisService;

    public String getResponse(String query) {
        String cachedResult = redisService.getFromCache(query);

        if (cachedResult != null) {
            return cachedResult;  // If cached, return immediately
        } else {
            // Step 2: Send query to RabbitMQ Producer
            queryProducer.sendQuery(query);

            // Step 3: Poll Redis for result
            int maxAttempts = 5;
            int attempt = 0;
            while (attempt < maxAttempts) {
                cachedResult = redisService.getFromCache(query);
                if (cachedResult != null) {
                    return cachedResult;  // Return when result is found
                }
                try {
                    Thread.sleep(1000);  // Wait for 1 second before checking again
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                attempt++;
            }

            // Step 4: If result not ready, inform the user
            return "Your request is being processed. Please try again later.";
        }

    }
}
