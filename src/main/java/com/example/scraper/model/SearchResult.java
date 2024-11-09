package com.example.scraper.model;

import java.time.ZonedDateTime;

public class SearchResult {
    private String url;
    private String matchedContent;
    private ZonedDateTime timestamp;

    public SearchResult(String url, String matchedContent, ZonedDateTime timestamp) {
        this.url = url;
        this.matchedContent = matchedContent;
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMatchedContent() {
        return matchedContent;
    }

    public void setMatchedContent(String matchedContent) {
        this.matchedContent = matchedContent;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
