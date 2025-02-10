package com.highperfromancecachingsystem.Service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToCache(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
