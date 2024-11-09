package com.example.scraper.model;

import java.time.ZonedDateTime;
import java.util.List;
public class ScrapeRequest {
    private List<String> urls;
    private List<String> keywords;
    private ZonedDateTime schedule;

    public ScrapeRequest() {
        // Default constructor for JSON deserialization
    }

    public ScrapeRequest(List<String> urls, List<String> keywords, ZonedDateTime schedule) {
        this.urls = urls;
        this.keywords = keywords;
        this.schedule = schedule;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public ZonedDateTime getSchedule() {
        return schedule;
    }

    public void setSchedule(ZonedDateTime schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "ScrapeRequest{" +
                "urls=" + urls +
                ", keywords=" + keywords +
                ", schedule=" + schedule +
                '}';
    }
}
