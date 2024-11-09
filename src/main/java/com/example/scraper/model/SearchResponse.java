package com.example.scraper.model;

import java.util.List;

public class SearchResponse {
    private String status;
    private List<SearchResult> results;


    public SearchResponse(String status, List<SearchResult> results) {
        this.status = status;
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }
}
