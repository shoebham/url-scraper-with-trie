package com.example.scraper.controller;


import com.example.scraper.model.ScrapeRequest;
import com.example.scraper.service.ScrapeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class ScraperController {

    Logger logger = LoggerFactory.getLogger(ScraperController.class);

    @Autowired
    private ScrapeService scrapeService;


    @PostMapping("/scrape")
    private void scrape(@RequestBody  ScrapeRequest scrapeRequest){
        scrapeService.scrape(scrapeRequest);
    }

}
