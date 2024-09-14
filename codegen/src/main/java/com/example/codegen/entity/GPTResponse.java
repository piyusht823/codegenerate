package com.example.codegen.entity;

import jakarta.persistence.*;

@Entity
public class GPTResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String prompt;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String response;

    @Column(nullable = false, length = 50)  // Store language detected from the code block
    private String language;

    @Column(nullable = false)  // Store the total number of lines of code
    private int totalLines;

    public GPTResponse() {}

    public GPTResponse(String prompt, String response, String language, int totalLines) {
        this.prompt = prompt;
        this.response = response;
        this.language = language;
        this.totalLines = totalLines;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }
}
