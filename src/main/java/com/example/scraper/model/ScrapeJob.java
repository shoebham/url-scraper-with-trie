package com.example.scraper.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "scrape_jobs")
public class ScrapeJob {
    @Id
    private String jobId;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private ZonedDateTime scheduledAt;
    private ZonedDateTime startedAt;
    private ZonedDateTime finishedAt;
    private List<String> urlsScraped;
    private List<String> keywords;
    private String dataSize;

    public ScrapeJob(){

    }
    public ScrapeJob(String jobId, JobStatus status, ZonedDateTime scheduledAt, ZonedDateTime startedAt, ZonedDateTime finishedAt, List<String> urlsScraped, String dataSize) {
        this.jobId = jobId;
        this.status = status;
        this.scheduledAt = scheduledAt;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.urlsScraped = urlsScraped;
        this.dataSize = dataSize;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public ZonedDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(ZonedDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public ZonedDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(ZonedDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public ZonedDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(ZonedDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public List<String> getUrlsScraped() {
        return urlsScraped;
    }

    public void setUrlsScraped(List<String> urlsScraped) {
        this.urlsScraped = urlsScraped;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }
}

