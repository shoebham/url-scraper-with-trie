package com.example.scraper.model;

import java.time.ZonedDateTime;
import java.util.Date;

public class ScrapeResponse {
    private String status;
    private String message;
    private String jobId;
    private ZonedDateTime scheduledAt;

    public ScrapeResponse(String status, String message, String jobId, ZonedDateTime scheduledAt) {
        this.status = status;
        this.message = message;
        this.jobId = jobId;
        this.scheduledAt = scheduledAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public ZonedDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(ZonedDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }
}