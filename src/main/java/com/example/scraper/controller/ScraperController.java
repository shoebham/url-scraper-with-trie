package com.example.scraper.controller;


import com.example.scraper.model.ScrapeRequest;
import com.example.scraper.model.ScrapeResponse;
import com.example.scraper.service.ScrapeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RequestMapping("/api/v1")
@RestController
public class ScraperController {

    Logger logger = LoggerFactory.getLogger(ScraperController.class);

    @Autowired
    private ScrapeService scrapeService;


    @PostMapping("/scrape")
    private ResponseEntity<ScrapeResponse> scrape(@RequestBody ScrapeRequest scrapeRequest) throws IOException, ExecutionException, InterruptedException {
        try {
            ScrapeResponse response = scrapeService.scrape(scrapeRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing scrape request: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
