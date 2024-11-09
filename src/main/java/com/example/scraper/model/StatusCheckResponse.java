package com.example.scraper.model;

import java.time.ZonedDateTime;
import java.util.List;

public class StatusCheckResponse {
    private String status;
    private String jobId;
    private List<String> urlsScraped;
    private List<String> keywordsFound;
    private String dataSize;
    private ZonedDateTime finishedAt;


    public StatusCheckResponse(String status, String jobId, List<String> urlsScraped, List<String> keywordsFound, String dataSize, ZonedDateTime finishedAt) {
        this.status = status;
        this.jobId = jobId;
        this.urlsScraped = urlsScraped;
        this.keywordsFound = keywordsFound;
        this.dataSize = dataSize;
        this.finishedAt = finishedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public List<String> getUrlsScraped() {
        return urlsScraped;
    }

    public void setUrlsScraped(List<String> urlsScraped) {
        this.urlsScraped = urlsScraped;
    }

    public List<String> getKeywordsFound() {
        return keywordsFound;
    }

    public void setKeywordsFound(List<String> keywordsFound) {
        this.keywordsFound = keywordsFound;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public ZonedDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(ZonedDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }
}
