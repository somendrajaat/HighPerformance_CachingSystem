package com.highperfromancecachingsystem.Controller;

import com.highperfromancecachingsystem.Service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public String generateText(@RequestParam String prompt) {
        return geminiService.generateText(prompt);
    }
}
