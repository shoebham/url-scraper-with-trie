package com.example.scraper.service;

import com.example.scraper.model.ScrapeRequest;
import com.example.scraper.model.ScrapeResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import com.example.scraper.utils.Trie;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
public class ScrapeService {

    @Autowired
    private Trie trie;
    Logger logger = LoggerFactory.getLogger(ScrapeService.class);

    public ScrapeResponse scrape(ScrapeRequest scrapeRequest) throws IOException {
        logger.info("Scrape Request: {} ", scrapeRequest);
        String jobId = UUID.randomUUID().toString();
        List<String> urls = scrapeRequest.getUrls();
        List<String> keywords = scrapeRequest.getKeywords();
        ZonedDateTime scheduledAt = scrapeRequest.getSchedule();

        CompletableFuture.runAsync(() -> {
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
            } catch (IOException e) {
                logger.error("Error during scraping: ", e);
                throw new CompletionException(e);
            }
        });

        return new ScrapeResponse(jobId, scheduledAt);
    }
}
