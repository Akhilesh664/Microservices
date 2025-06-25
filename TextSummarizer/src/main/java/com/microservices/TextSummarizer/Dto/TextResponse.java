package com.microservices.TextSummarizer.Dto;

public class TextResponse {
    private String summary;
    public TextResponse(String summary) { this.summary = summary; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}