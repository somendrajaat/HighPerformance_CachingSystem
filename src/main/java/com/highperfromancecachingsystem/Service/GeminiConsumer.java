package com.highperfromancecachingsystem.Service;

import com.highperfromancecachingsystem.Model.GeminiRequest;
import com.highperfromancecachingsystem.Model.GeminiResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class GeminiConsumer {

    private final RedisTemplate<String, String> redisTemplate;
    private final WebClient webClient;
    private static final String API_KEY = "GEMINI_API_KEY";
    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    public GeminiConsumer(RedisTemplate<String, String> redisTemplate, WebClient.Builder webClientBuilder) {
        this.redisTemplate = redisTemplate;
        this.webClient = webClientBuilder.baseUrl(GEMINI_URL).build();
    }

    @RabbitListener(queues = "queryQueue")
    public String processRequest(String request) {
        String prompt = request;

        // Call Gemini API
        GeminiResponse response = webClient.post()
                .bodyValue("{\"contents\": [{\"parts\": [{\"text\": \"" + prompt + "\"}]}]}")
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .block();

        if (response != null) {
            String aiResponse = response.getCandidates().get(0).getContent().getPart().get(0).getText();
            redisTemplate.opsForValue().set(prompt, aiResponse, Duration.ofHours(1));
        }
        return response.getCandidates().get(0).getContent().getPart().get(0).getText();
    }
}
