package com.example.scraper.service;

import com.example.scraper.model.ScrapeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScrapeService {

    Logger logger = LoggerFactory.getLogger(ScrapeService.class);

    public void scrape(ScrapeRequest scrapeRequest){
        logger.info("Scrape Request: {} ",scrapeRequest);
    }
}
