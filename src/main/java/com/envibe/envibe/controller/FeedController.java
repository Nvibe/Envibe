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
}
