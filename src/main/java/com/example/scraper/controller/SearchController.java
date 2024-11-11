package com.example.scraper.controller;


import com.example.scraper.model.*;
import com.example.scraper.repository.ScrapeJobRepository;
import com.example.scraper.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchService searchService;

    @Autowired
    private ScrapeJobRepository scrapeJobRepository;

    @PostMapping("/search")
    private ResponseEntity<SearchResponse> search(@RequestBody SearchRequest request){
        try{
            List<SearchResult> results = searchService.search(request.getPrefix(), request.getLimit());
            return ResponseEntity.ok(new SearchResponse("success",results));
        }catch (Exception e){
            logger.error("Error processing search request: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/status/{jobId}")
    private ResponseEntity<StatusCheckResponse> getStatus(@PathVariable String jobId){
        try {
            ScrapeJob job = scrapeJobRepository.findById(jobId)
                    .orElseThrow(() -> new RuntimeException("Job not found"));

            return ResponseEntity.ok(new StatusCheckResponse(
                    job.getStatus().toString(),
                    job.getJobId(),
                    job.getUrlsScraped(),
                    job.getKeywords(),
                    job.getDataSize(),
                    job.getFinishedAt()
            ));
        }catch (Exception e) {
            logger.error("Error retrieving job status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
