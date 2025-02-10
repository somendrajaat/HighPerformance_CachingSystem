package com.highperfromancecachingsystem.Controller;
import com.highperfromancecachingsystem.Service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {
    @Autowired
    CacheService cacheService;
    @GetMapping("query")
    public ResponseEntity<?> query(@RequestParam String query) {
        return ResponseEntity.ok(cacheService.getResponse(query));
    }

}
