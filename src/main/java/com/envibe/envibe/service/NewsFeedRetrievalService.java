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
        // Get the post_ids of the cached feed of the specified user.
        int[] idReferenceArray = getAllPostIndexes(username);
        // Return an empty list if no cache was found or the specified user does not exist.
        // TODO: Throw an exception if we go the API route so we can customize the error message on the frontend.
        if (idReferenceArray.length == 0) return new ArrayList<NewsItem>();
        // Calculate the index of idReferenceArray to start at.
        int startIndex = getStartIndex(idReferenceArray, after);
        // Check if the +1 put the startIndex past the bounds of the array. If so, we have reached the end of the
        // feed and can return an empty ArrayList.
        if (startIndex >= idReferenceArray.length) return new ArrayList<NewsItem>();
        // Calculate the index of idReferenceArray to end at (or the end of the array, whichever is shorter.
        int endIndex = Math.min(startIndex + count, idReferenceArray.length - 1);
        // Return the loaded NewsItems for the portion of post_ids in idReferenceArray that we need.
        return getItemsBetweenIndexes(idReferenceArray, startIndex, endIndex);
    }

    /**
     * Retrieves a user's feed cache that contains post_ids.
     * @param username Username to include in search tag.
     * @return Integer array of post_ids.
     */
    private int[] getAllPostIndexes(String username) {
        // Create a search tag based on our assigned purpose and the username we are looking for.
        String tag = cachedItemDao.generateTag(CachedItemDao.PURPOSE_NEWS_FEED_CACHE, username);
        // Load the raw CachedItem from the temporary datastore.
        CachedItem cachedFeed = cachedItemDao.read(tag);
        // Return an empty array if the cache was not found for the specified user.
        // TODO: Throw an exception.
        if (cachedFeed == null) return new int[0];
        // Get the String that contains the list of post_ids.
        String idFeed = cachedFeed.getPayload();
        // Return an empty array if the feed is empty.
        if (idFeed.isEmpty()) return new int[0];
        // Split the feed into an array so we can address each post_id.
        String[] idArray = idFeed.split(",");
        // Convert the String[] array to an int[] array.
        int[] idReferenceArray = new int[idArray.length];
        for (int i = 0; i < idArray.length; i++) {
            idReferenceArray[i] = Integer.parseInt(idArray[i]);
        }
        // Return the result.
        return idReferenceArray;
    }

    /**
     * Calculates the index in idReferenceArray to start at. Returns 0 if not found.
     * @param idReferenceArray Array of post_ids to search for after value.
     * @param after Last post_id received by client.
     * @return The index following the index of after in idReferenceArray.
     */
    private int getStartIndex(int[] idReferenceArray, int after) {
        // If after is the special FROM_BEGINNING value, return that we should start at the beginning of the array.
        if (after == FROM_BEGINNING) return 0;
        // Set the starting index to zero unless after is found.
        int startIndex = 0;
        // Iterate through idReferenceArray looking for the post_id defined by after.
        for (int i = 0; i < idReferenceArray.length; i++) {
            if(idReferenceArray[i] == after) {
                // We found it. Increment i by one to have us start at the next index and break the loop.
                startIndex = i + 1;
                break;
            }
        }
        // Return the result.
        return startIndex;
    }

    /**
     * Returns a subset of an array of post_ids with all values between two indexes loaded (inclusive).
     * @param idReferenceArray Array that holds post_id values.
     * @param startIndex Index to start sublist at.
     * @param endIndex Index to end sublist at.
     * @return Sublist of idReferenceArray with loaded NewsItems.
     */
    private List<NewsItem> getItemsBetweenIndexes(int[] idReferenceArray, int startIndex, int endIndex) {
        // Create list to hold all of the loaded posts.
        ArrayList<NewsItem> returnPostings = new ArrayList<NewsItem>();
        // Iterate from the starting index to the ending index.
        for (int i = startIndex; i < endIndex; i++) {
            // For each post_id in idReferenceArray, load the actual post from the database.
            // TODO: Probably shouldn't be performing 10 different calls to the database. Convert to a bulk call.
            NewsItem newsItem = newsItemDao.read(idReferenceArray[i]);
            // If the post exists, add it to the list.
            if (newsItem != null) {
                returnPostings.add(newsItem);
            }
        }
        // Return the result.
        return returnPostings;
    }
}
