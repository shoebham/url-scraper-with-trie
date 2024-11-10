package com.example.scraper.utils;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    private static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        HashMap<String,String> contentList;

        TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
            contentList = new HashMap<>();
        }
    }


    public void insert(String word, String url, String matchedContent) {
        if (word == null || word.isEmpty()) {
            return;
        }

        TrieNode current = root;
        word = word.toLowerCase();

        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }

        current.isEndOfWord = true;
        current.contentList.put(url, matchedContent);
    }

    public Map<String, String> search(String prefix, int limit) {
        if (prefix == null || prefix.isEmpty()) {
            return new HashMap<>();
        }

        TrieNode node = searchNode(prefix.toLowerCase());
        if (node == null) {
            return new HashMap<>();
        }

        Map<String, String> results = new HashMap<>();
        helper(node,results,limit);
        return results;
    }

    private void helper(TrieNode node,Map<String,String> results,int limit){

        if (node.isEndOfWord) {
            // Only collect results if we've found an exact match for the prefix
            for (String url : node.contentList.keySet()) {
                String content = node.contentList.get(url);
                if (results.size() < limit) {
                    if (!results.containsKey(url)) {
                        results.put(url, content);
                    }
                }
            }
        }

        for(TrieNode child: node.children.values()) {
            if (results.size() > limit) {
                break;
            }
            helper(child, results, limit);
        }
    }

    private TrieNode searchNode(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return null;
            }
            current = current.children.get(ch);
        }
        return current;
    }

}