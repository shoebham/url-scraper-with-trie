package com.example.scraper.repository;

import com.example.scraper.model.ScrapeJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapeJobRepository extends JpaRepository<ScrapeJob, String> {
}