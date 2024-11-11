package com.example.scraper.repository;

import com.example.scraper.model.ScrapedContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapedContentRepository extends JpaRepository<ScrapedContent, Long> {
    List<ScrapedContent> findByKeywordStartingWith(String prefix);
}