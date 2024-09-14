package com.example.codegen.service;

import com.example.codegen.repository.LanguageLineCount;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import com.example.codegen.entity.GPTResponse;
import com.example.codegen.repository.GPTResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;


@Service
public class GPTService {

    private final ChatLanguageModel gemini;

    @Autowired
    private GPTResponseRepository gptResponseRepository;

    public List<LanguageLineCount> getMaxLinesPerLanguage() {
        return gptResponseRepository.findMaxLinesPerLanguage();
    }

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    private static final String CODE_BLOCK_START = "```";
    private static final String CODE_BLOCK_END = "```";

    @Autowired
    public GPTService(@Value("${gemini.api.key}") String apiKey) {
        this.gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-1.5-flash")  // Gemini model version
                .build();
    }

    public GPTResponse generateResponse(String input) {
        // Create the ChatRequest using the Google Gemini AI model
        ChatResponse chatResponse = gemini.chat(ChatRequest.builder()
                .messages(UserMessage.from(input))
                .build());

        // Extract the AI response
        String response = chatResponse.aiMessage().text();

        // Extract language and line count
        String language = extractLanguageFromResponse(response);
        int totalLines = countLinesOfCode(response);

        // Log the response and extracted details for debugging
        System.out.println("Generated Response:\n" + response);
        System.out.println("Detected Language: " + language);
        System.out.println("Total Lines of Code: " + totalLines);

        // Save response, language, and totalLines to the database
        GPTResponse gptResponse = new GPTResponse(input, response, language, totalLines);
        GPTResponse savedResponse = gptResponseRepository.save(gptResponse);

        // Update Redis with the total lines of code per language
        updateRedisCache(language, totalLines);

        return savedResponse;
    }

    // Extract the first word after ``` as the language
    private String extractLanguageFromResponse(String response) {
        int startIndex = response.indexOf(CODE_BLOCK_START);
        if (startIndex != -1) {
            startIndex += CODE_BLOCK_START.length();
            int endIndex = response.indexOf("\n", startIndex);  // End of language definition
            if (endIndex != -1) {
                return response.substring(startIndex, endIndex).trim();  // Extract the language name
            }
        }
        return "Unknown";  // Fallback if no language is found
    }

    // Count the number of lines of code within the code block
    private int countLinesOfCode(String response) {
        int startIndex = response.indexOf(CODE_BLOCK_START);
        int endIndex = response.lastIndexOf(CODE_BLOCK_END);
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            String codeBlock = response.substring(startIndex + CODE_BLOCK_START.length(), endIndex).trim();
            return codeBlock.split("\n").length;  // Split code block into lines and count them
        }
        return 0;  // Fallback if no code block is found
    }

    // Update Redis cache with total lines of code for the detected language
    private void updateRedisCache(String language, int totalLines) {
        // Get current total from Redis
        Integer currentTotalLines = redisTemplate.opsForValue().get(language);
        if (currentTotalLines == null) {
            currentTotalLines = 0;  // If no value exists, start from 0
        }

        // Update Redis with the new total
        redisTemplate.opsForValue().set(language, currentTotalLines + totalLines);
    }

}


