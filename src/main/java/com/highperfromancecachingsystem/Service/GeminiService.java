package com.highperfromancecachingsystem.Service;

import com.highperfromancecachingsystem.Configuration.EnvConfig;
import com.highperfromancecachingsystem.Model.GeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class GeminiService {

    private final WebClient webClient;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String API_KEY = EnvConfig.getGeminiApiKey();
    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    @Autowired
    public GeminiService(WebClient.Builder webClientBuilder, RedisTemplate<String, String> redisTemplate) {
        this.webClient = webClientBuilder.baseUrl(GEMINI_URL).build();
        this.redisTemplate = redisTemplate;
    }

    public String generateText(String prompt) {
        // Check if response exists in Redis
        String cachedResponse = redisTemplate.opsForValue().get(prompt);
        if(cachedResponse!=null){
            return cachedResponse;
        }
        // If not cached, call the API
        GeminiResponse response = webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue("{\"contents\": [{\"parts\": [{\"text\": \"" + prompt + "\"}]}]}")
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .block();

        // Store response in Redis
        if (response != null) {
            redisTemplate.opsForValue().set(prompt, response
                            .candidates
                    .get(0)
                    .content
                    .getPart()
                    .get(0)
                    .getText()
                    , Duration.ofHours(1)); // Cache for 1 hour
        }

        assert response != null;
        GeminiResponse gr =new GeminiResponse();
        gr.candidates=response.candidates;
        return gr.getCandidates().get(0).getContent().getPart().get(0).getText();
    }
}
