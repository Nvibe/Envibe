package com.envibe.envibe.controller;

import com.envibe.envibe.model.NewsItem;
import com.envibe.envibe.service.NewsFeedRetrievalService;
import com.envibe.envibe.service.NewsFeedUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles both browser requests and API calls related to user newsfeeds.
 *
 * @author ARMmaster17
 */
@Controller
public class FeedController {

    @Autowired
    NewsFeedRetrievalService newsFeedRetrievalService;

    /**
     * Returns a view that can display news feed posts.
     * @param model Container we can use to inject data into the view.
     * @return View template to render.
     */
    @GetMapping("/feed")
    public String userFeed(Model model) {
        return "Feed";
    }

    /**
     * Handles API requests for a user's cached newsfeed. URI follows the format /api/v1/feed?username=user1&count=10&after=4012.
     * @param model Container we can use to inject data into the view.
     * @param username Username to search for in cache tags.
     * @param count Number of posts to return.
     * @param after Last post_id received by the client.
     * @return List of news items with specified parameters.
     */
    @GetMapping("/api/v1/feed")
    public List<NewsItem> apiUserFeed(Model model, @RequestParam String username, @RequestParam(required = false) int count, @RequestParam(required = false) int after) {
        if (count == 0) {
            return newsFeedRetrievalService.getNewsFeed(username);
        } else if (after == 0) {
            return newsFeedRetrievalService.getNewsFeed(username, count);
        } else {
            return newsFeedRetrievalService.getNewsFeed(username, count, after);
        }
    }
}
