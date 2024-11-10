package com.example.scraper.service;

import com.example.scraper.model.ScrapeRequest;
import com.example.scraper.model.ScrapeResponse;
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
import java.util.Date;
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

    private static final Logger logger = LoggerFactory.getLogger(ScrapeService.class);

    // Store scheduled tasks with their job IDs
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public ScrapeResponse scrape(ScrapeRequest scrapeRequest) throws IOException {
        logger.info("Scrape Request: {} ", scrapeRequest);
        String jobId = UUID.randomUUID().toString();
        ZonedDateTime scheduledAt = scrapeRequest.getSchedule();
        scheduledAt = ZonedDateTime.now().plusSeconds(10);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(
                ()-> executeScrapingTask(scrapeRequest,jobId),
                scheduledAt.toInstant()
        );

        scheduledTasks.put(jobId, scheduledTask);
        logger.info("Scheduled scraping task with jobId: {} for time: {}", jobId, scheduledAt);

        return new ScrapeResponse(jobId, scheduledAt);
    }

    private void executeScrapingTask(ScrapeRequest scrapeRequest,String jobId) {
        List<String> urls = scrapeRequest.getUrls();
        List<String> keywords = scrapeRequest.getKeywords();
        try {
            for (String url : urls) {
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .timeout(5000)
                        .get();

                String content = doc.text();

                for (String keyword : keywords) {
                    if (content.toLowerCase().contains(keyword.toLowerCase())) {
                        trie.insert(keyword, url, content);
                    }
                }
            }
            logger.info("Completed scraping task for jobId: {}", jobId);
        } catch (IOException e) {
            logger.error("Error during scraping for jobId: " + jobId, e);
            throw new RuntimeException("Failed to execute scraping task", e);
        } finally {
            // Clean up the scheduled task reference
            scheduledTasks.remove(jobId);
        }
    }
}
