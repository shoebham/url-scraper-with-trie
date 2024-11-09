package com.example.scraper.model;

public class StatusRequest {
    private String jobId;

    public StatusRequest() {
        // Default constructor for JSON deserialization
    }

    public StatusRequest(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "StatusRequest{" +
                "jobId='" + jobId + '\'' +
                '}';
    }
}