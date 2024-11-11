package com.example.scraper.service;

import com.example.scraper.model.*;
import com.example.scraper.repository.ScrapeJobRepository;
import com.example.scraper.repository.ScrapedContentRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import com.example.scraper.utils.Trie;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class ScrapeService {

    @Autowired
    private Trie trie;


    @Autowired
    private TaskScheduler taskScheduler;


    @Autowired
    private ScrapeJobRepository scrapeJobRepository;

    @Autowired
    private ScrapedContentRepository scrapedContentRepository;
    private static final Logger logger = LoggerFactory.getLogger(ScrapeService.class);

    // Store scheduled tasks with their job IDs
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public ScrapeResponse scrape(ScrapeRequest scrapeRequest) throws IOException {
        logger.info("Scrape Request: {} ", scrapeRequest);
        String jobId = UUID.randomUUID().toString();
        ZonedDateTime scheduledAt = scrapeRequest.getSchedule() != null ?
                scrapeRequest.getSchedule() : ZonedDateTime.now().plusSeconds(10);


        // Create and save job record
        ScrapeJob job = new ScrapeJob();
        job.setJobId(jobId);
        job.setStatus(JobStatus.SCHEDULED);
        job.setScheduledAt(scheduledAt);
        scrapeJobRepository.save(job);


        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(
                ()-> executeScrapingTask(scrapeRequest,jobId),
                scheduledAt.toInstant()
        );

        scheduledTasks.put(jobId, scheduledTask);
        logger.info("Scheduled scraping task with jobId: {} for time: {}", jobId, scheduledAt);

        return new ScrapeResponse(jobId, scheduledAt);
    }

    private void executeScrapingTask(ScrapeRequest scrapeRequest,String jobId) {
        ScrapeJob job = scrapeJobRepository.findById(jobId).orElseThrow();
        job.setStatus(JobStatus.SCRAPING);
        job.setStartedAt(ZonedDateTime.now());
        scrapeJobRepository.save(job);

        List<String> urls = scrapeRequest.getUrls();
        List<String> keywords = scrapeRequest.getKeywords();
        long totalBytes = 0;
        try {
            for (String url : urls) {
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .timeout(5000)
                        .get();

                String content = doc.text();
                totalBytes += content.getBytes().length;

                for (String keyword : keywords) {
                    if (content.toLowerCase().contains(keyword.toLowerCase())) {
                        trie.insert(keyword, url, content);

                        // Store in database
                        ScrapedContent scrapedContent = new ScrapedContent();
                        scrapedContent.setUrl(url);
                        scrapedContent.setKeyword(keyword);
                        scrapedContent.setMatchedContent(extractRelevantContent(content, keyword));
                        scrapedContent.setTimestamp(ZonedDateTime.now());
                        scrapedContentRepository.save(scrapedContent);
                    }
                }
            }
            // Update job status to completed
            job.setStatus(JobStatus.COMPLETED);
            job.setFinishedAt(ZonedDateTime.now());
            job.setUrlsScraped(urls);
            job.setKeywords(keywords);
            job.setDataSize(formatDataSize(totalBytes));
            scrapeJobRepository.save(job);

            logger.info("Completed scraping task for jobId: {}", jobId);
        } catch (IOException e) {
            job.setStatus(JobStatus.FAILED);
            job.setFinishedAt(ZonedDateTime.now());
            scrapeJobRepository.save(job);

            logger.error("Error during scraping for jobId: " + jobId, e);
            throw new RuntimeException("Failed to execute scraping task", e);
        } finally {
            // Clean up the scheduled task reference
            scheduledTasks.remove(jobId);
        }
    }
    private String extractRelevantContent(String content, String keyword) {
        int index = content.toLowerCase().indexOf(keyword.toLowerCase());
        if (index == -1) return "";
        int start = Math.max(0, index - 50);
        int end = Math.min(content.length(), index + keyword.length() + 50);
        return content.substring(start, end) + "...";
    }

    private String formatDataSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return (bytes / 1024) + " KB";
        return (bytes / (1024 * 1024)) + " MB";
    }
}
