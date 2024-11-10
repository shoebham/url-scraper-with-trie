package com.example.scraper.service;

import com.example.scraper.model.ScrapeRequest;
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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ScrapeService {

    @Autowired
    private Trie trie;
    Logger logger = LoggerFactory.getLogger(ScrapeService.class);

    @Async
    public CompletableFuture<String> scrape(ScrapeRequest scrapeRequest) throws IOException {
        logger.info("Scrape Request: {} ", scrapeRequest);
        String jobId = UUID.randomUUID().toString();
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
                        trie.insert(keyword, url,content);
                    }
                }
                logger.info("Results:{}",trie.search("ind",5));
            }
            return CompletableFuture.completedFuture(jobId);
        } catch (IOException e) {
            throw new RuntimeException("Failed to scrape websites", e);
        }
    }
}
