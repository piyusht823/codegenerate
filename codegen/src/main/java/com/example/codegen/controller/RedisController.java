package com.example.codegen.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/stats")
public class RedisController {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @GetMapping("/total-lines")
    public Map<String, Integer> getTotalLinesOfCodeByLanguage() {
        Set<String> languages = redisTemplate.keys("*");
        Map<String, Integer> totalLinesPerLanguage = new HashMap<>();

        for (String language : languages) {
            Integer totalLines = redisTemplate.opsForValue().get(language);
            totalLinesPerLanguage.put(language, totalLines);
        }

        return totalLinesPerLanguage;
    }
}



