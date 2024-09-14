package com.example.codegen.controller;

import com.example.codegen.repository.LanguageLineCount;
import com.example.codegen.service.GPTService;
import com.example.codegen.entity.GPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class GPTController {

    @Autowired
    private GPTService gptService;

//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/generate")
//    public GPTResponse generateResponse(@RequestParam String input) {
//        return gptService.generateResponse(input);
//    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/generate")
    public GPTResponse generateResponse(@RequestBody GPTRequest request) {
        return gptService.generateResponse(request.getInput());
    }
    
    // API to fetch maximum lines of code for each language
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/languages/max-lines")
    public List<LanguageLineCount> getMaxLinesPerLanguage() {
        return gptService.getMaxLinesPerLanguage();
    }
}


