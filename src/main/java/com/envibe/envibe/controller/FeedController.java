package com.envibe.envibe.controller;

import com.envibe.envibe.dao.NewsItemDao;
import com.envibe.envibe.model.NewsItem;
import com.envibe.envibe.service.NewsFeedRetrievalService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Handles both browser requests and API calls related to user newsfeeds.
 *
 * @author ARMmaster17
 */
@Controller
public class FeedController {

    /**
     * Injected service that handles retrieving news feed caches.
     */
    @Autowired
    NewsFeedRetrievalService newsFeedRetrievalService;

    /**
     * Injected data access object for accessing user posts in the permanent datastore.
     */
    @Autowired
    NewsItemDao newsItemDao;

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
     * @param request Access class that allows us to read session data from the user's browser.
     * @param count Number of posts to return.
     * @param after Last post_id received by the client.
     * @return List of news items with specified parameters.
     */
    @GetMapping(value = "/api/v1/feed/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String apiUserFeed(Model model, HttpServletRequest request, @RequestParam(required = false) int count, @RequestParam(required = false) int after) {
        List<NewsItem> newsFeed;
        if (count == 0) {
            newsFeed = newsFeedRetrievalService.getNewsFeed(request.getRemoteUser());
        } else if (after == 0) {
            newsFeed = newsFeedRetrievalService.getNewsFeed(request.getRemoteUser(), count);
        } else {
            newsFeed = newsFeedRetrievalService.getNewsFeed(request.getRemoteUser(), count, after);
        }
        String results = new Gson().toJson(newsFeed);
        return results;
    }

    /**
     * Creates a post and fires off a background worker that rebuilds the news feeds of all the user's friends.
     * @param model Container that we can use to inject data into the view.
     * @return Redirect to the news feed view.
     */
    @PostMapping("/api/v1/feed/create")
    public String apiTestAddPost(Model model, HttpServletRequest request, @RequestParam(value="content", required=true) String content) {
        NewsItem ni = new NewsItem(request.getRemoteUser(), new Date(), content);
        newsItemDao.create(ni);
        return "Feed";
    }
}
