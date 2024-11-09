package com.example.scraper.model;

public class SearchRequest {
    private String prefix;
    private Integer limit;

    public SearchRequest() {
        // Default constructor for JSON deserialization
    }

    public SearchRequest(String prefix, Integer limit) {
        this.prefix = prefix;
        this.limit = limit;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "prefix='" + prefix + '\'' +
                ", limit=" + limit +
                '}';
    }
}
