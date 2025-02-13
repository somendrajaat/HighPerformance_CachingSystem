package com.highperfromancecachingsystem.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Value("${GEMINI_API_KEY}")
    public static String GEMINI_API_KEY = "GEMINI_API_KEY";
    public static  String getGeminiApiKey() {
        return GEMINI_API_KEY;
    }
}
