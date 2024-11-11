package com.example.scraper.service;

import com.example.scraper.model.ScrapedContent;
import com.example.scraper.model.SearchResult;
import com.example.scraper.repository.ScrapedContentRepository;
import com.example.scraper.utils.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private ScrapedContentRepository scrapedContentRepository;

    @Autowired
    private Trie trie;

    public List<SearchResult> search(String prefix, int limit){
        return scrapedContentRepository.findByKeywordStartingWith(prefix).stream().limit(limit).map(this::convertToSearchResult).collect(Collectors.toList());
    }

    private SearchResult convertToSearchResult(ScrapedContent content) {
        return new SearchResult(
                content.getUrl(),
                content.getMatchedContent(),
                content.getTimestamp()
        );
    }
}
