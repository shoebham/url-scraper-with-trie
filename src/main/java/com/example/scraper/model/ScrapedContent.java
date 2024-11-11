package com.example.scraper.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "scraped_content")
public class ScrapedContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String keyword;
    private String matchedContent;
    private ZonedDateTime timestamp;

    public ScrapedContent(){

    }
    public ScrapedContent(Long id, String url, String keyword, String matchedContent, ZonedDateTime timestamp) {
        this.id = id;
        this.url = url;
        this.keyword = keyword;
        this.matchedContent = matchedContent;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
