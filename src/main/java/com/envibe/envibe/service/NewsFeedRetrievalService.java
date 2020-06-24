package com.envibe.envibe.service;

import com.envibe.envibe.dao.CachedItemDao;
import com.envibe.envibe.dao.NewsItemDao;
import com.envibe.envibe.model.CachedItem;
import com.envibe.envibe.model.NewsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the retrieval of a pre-processed feed cache associated with a registered user.
 *
 * @author ARMmaster17
 */
@Service
public class NewsFeedRetrievalService {

    /**
     * Injected data access object for items in the temporary datastore.
     */
    @Autowired
    CachedItemDao cachedItemDao;

    /**
     * Injected data access object for news feed caches in the temporary datastore.
     */
    @Autowired
    NewsItemDao newsItemDao;

    /**
     * Sets the default number of posts to return if count is not specified.
     */
    private static final int DEFAULT_POST_COUNT = 10;

    /**
     * Sets the default indicator that a request should retrieve posts from the beginning.
     */
    private static final int FROM_BEGINNING = -1;

    /**
     * Retrieves a list of posts to populate a user's newsfeed.
     * @param username Username to perform the lookup under.
     * @return List of posts from user's newsfeed.
     */
    public List<NewsItem> getNewsFeed(String username) {
        return getNewsFeed(username, DEFAULT_POST_COUNT, FROM_BEGINNING);
    }

    /**
     * Retrieves a list of posts to populate a user's newsfeed.
     * @param username Username to perform the lookup under.
     * @param count Number of posts to return.
     * @return List of posts from user's newsfeed.
     */
    public List<NewsItem> getNewsFeed(String username, int count) {
        return getNewsFeed(username, count, FROM_BEGINNING);
    }

    /**
     * Retrieves a list of posts to populate a user's newsfeed.
     * @param username Username to perform the lookup under.
     * @param count Number of posts to return.
     * @param after Defines the last post_id that was received.
     * @return List of posts from user's newsfeed.
     */
    public List<NewsItem> getNewsFeed(String username, int count, int after) {
        String tag = cachedItemDao.generateTag(CachedItemDao.PURPOSE_NEWS_FEED_CACHE, username);
        CachedItem cachedFeed = cachedItemDao.read(tag);
        if (cachedFeed == null) return new ArrayList<NewsItem>();
        String idFeed = cachedFeed.getPayload();
        if (idFeed.isEmpty()) return new ArrayList<NewsItem>();
        String[] idArray = idFeed.split(",");
        int[] idReferenceArray = new int[idArray.length];
        for (int i = 0; i < idArray.length; i++) {
            idReferenceArray[i] = Integer.parseInt(idArray[i]);
        }
        int startIndex = 0;
        if (after != FROM_BEGINNING) {
            for (int i = 0; i < idReferenceArray.length; i++) {
                if(idReferenceArray[i] == after) {
                    startIndex = i + 1;
                    break;
                }
            }
        }
        int endIndex = Math.min(startIndex + count, idReferenceArray.length - 1);
        ArrayList<NewsItem> returnPostings = new ArrayList<NewsItem>();
        for (int i = startIndex; i < endIndex; i++) {
            NewsItem newsItem = newsItemDao.read(idReferenceArray[i]);
            returnPostings.set(i, newsItem);
        }
        return returnPostings;
    }
}
