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
            return cachedResult;
        } else {

            queryProducer.sendQuery(query);
            int maxAttempts = 5;
            int attempt = 0;
            while (attempt < maxAttempts) {
                cachedResult = redisService.getFromCache(query);
                if (cachedResult != null) {
                    return cachedResult;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                attempt++;
            }
            return "Your request is being processed. Please try again later.";
        }

    }
}
