package com.example.codegen.repository;

public class LanguageLineCount {

    private String language;
    private int totalLines;

    public LanguageLineCount(String language, int totalLines) {
        this.language = language;
        this.totalLines = totalLines;
    }

    // Getters and Setters
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
