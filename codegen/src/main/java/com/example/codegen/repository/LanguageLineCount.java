package com.example.codegen.repository;

public class LanguageLineCount {
    private String language;
    private double totalLines;

    public LanguageLineCount(String language, double totalLines) {
        this.language = language;
        this.totalLines = totalLines;
    }

    // Getters and setters
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(double totalLines) {
        this.totalLines = totalLines;
    }
}
