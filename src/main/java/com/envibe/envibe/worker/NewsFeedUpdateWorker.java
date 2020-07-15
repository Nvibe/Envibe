package com.envibe.envibe.worker;

import com.envibe.envibe.dao.CachedItemDao;
import com.envibe.envibe.dao.NewsItemDao;
import com.envibe.envibe.dao.UserDao;
import com.envibe.envibe.model.CachedItem;
import com.envibe.envibe.model.NewsItem;
import com.envibe.envibe.model.User;
import com.envibe.envibe.service.NewsFeedUpdateService;
import com.envibe.envibe.service.RelationshipDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the updating of the news feed in the background whenever a new post is created. Launched by {@link NewsFeedUpdateService}.
 * @see com.envibe.envibe.service.NewsFeedUpdateService
 *
 * @author ARMmaster17
 */
@Component
public class NewsFeedUpdateWorker implements Runnable {

    /**
     * Injected data access object for the NewsItem model.
     */
    @Autowired
    NewsItemDao newsItemDao;

    /**
     * Injected data access object for the User model.
     */
    @Autowired
    UserDao userDao;

    /**
     * Injected data access object for the CachedItem model.
     */
    @Autowired
    CachedItemDao cachedItemDao;

    private static final String INTERNAL_USER_TAG = "INTERNAL";

    /**
     * Injected service for retrieving a list of followers of a specified user.
     */
    @Autowired
    RelationshipDisplayService relationshipDisplayService;

    /**
     * Constructor that retrieves the newest post ID from memcache.
     */
    public NewsFeedUpdateWorker() {

    }

    /**
     * Triggered by super Thread instance. Runs the background task until completion, then the Thread super class performs automated garbage collection.
     */
    public void run() {
        // Update and save all necessary friend newsfeeds.
        updateFeeds();
    }

    /**
     * Retrieves a post ID from the pool of recent posts that need to be processed.
     * @return Next post ID in queue.
     */
    private int getNextPostId() {
        // Generate the tag to search for in the temporary keystore.
        String serviceTag = cachedItemDao.generateTag(CachedItemDao.PURPOSE_NEWS_FEED_WORKER_PASSTHROUGH, INTERNAL_USER_TAG);
        // Find the post ID that needs to be updated. Allow the DAO to do the delete to prevent a race condition.
        return Integer.parseInt(cachedItemDao.readAndDelete(serviceTag).getPayload());
    }

    /**
     *  Returns the username of the user that submitted the specified post.
     * @param nextPostId Assigned post ID to search for.
     * @return User object associated with the author of the specified post.
     */
    private User getUserOfNextPost(int nextPostId) {
        // Pull the full post details from the database.
        NewsItem originalPost = newsItemDao.read(nextPostId);
        // Pull the entire profile of the user who created the post.
        return userDao.read(originalPost.getUsername());
    }

    /**
     * Gets a list of all usernames of accounts that should have their newsfeeds updated because of the specified post.
     * @param post_id Assigned post ID.
     * @return List of usernames of followers.
     */
    private ArrayList<String> getAffectedFriends(int post_id) {
        User poster = getUserOfNextPost(post_id);
        // Get a list of all the friends of the original user. Use Strings since we don't need any friend User attributes.
        ArrayList<String> friends = new ArrayList<>(relationshipDisplayService.FriendsList(poster.getUsername()));
        // Allow users to see their own posts on their feed.
        if (friends == null) friends = new ArrayList<>();
        friends.add(poster.getUsername());
        return friends;
    }

    /**
     * Gets a collection of all the caches that need to be updated because of the specified post.
     * @param post_id Assigned post ID.
     * @return Collection of newsfeed caches to update.
     */
    private List<CachedItem> getAllFriendCaches(int post_id) {

        // Get a list of all usernames whose newsfeeds are affected by this post.
        ArrayList<String> friends = getAffectedFriends(post_id);
        // Create a list to hold all of the cached newsfeeds.
        ArrayList<CachedItem> caches = new ArrayList<>();
        // Load the caches into the result list.
        for (String friend : friends) {
            caches.add(getOrCreateFeed(friend));
        }
        return caches;
    }

    /**
     * Gets a newsfeed cache associated with a specific user. If it doesn't exist, one is initialized.
     * @param friend_name Username associated with newsfeed cache to look up.
     * @return Newsfeed cache for specified user.
     */
    private CachedItem getOrCreateFeed(String friend_name) {
        // Generate a tag from our service name and the friend's username.
        String tag = cachedItemDao.generateTag(CachedItemDao.PURPOSE_NEWS_FEED_CACHE, friend_name);
        // Get a copy of the newsfeed cache for the specified user.
        CachedItem result = cachedItemDao.read(tag);
        // Check if the cache exists.
        if(result == null) {
            // Cache does not exist. Create a new one.
            //result = new CachedItem(tag, "");
            result = new CachedItem(cachedItemDao.PURPOSE_NEWS_FEED_CACHE, friend_name, "");
        }
        return result;
    }

    /**
     * Gets a post ID assignment, then retrieves, updates, and saves all newsfeeds that need to be updated.
     */
    private void updateFeeds() {
        // Get the next available post ID from the pool of posts that need to be processed.
        int post_id = getNextPostId();
        // Iterate through all friend caches, one at a time.
        for (CachedItem feed : getAllFriendCaches(post_id)) {
            // Get the updated feed string from the sorting algorithm.
            String updatedFeed = insertPostIntoFeed(feed.getPayload(), post_id);
            // Add the updated feed string back into the feed cache.
            feed.setPayload(updatedFeed);
            // Save the updated cache item into the permanent keystore.
            cachedItemDao.update(feed);
        }
    }

    /**
     * Implements algorithm that re-arranges newsfeed cache strings.
     * @param feed Existing cached feed in permanent keystore.
     * @param post_id Post to insert into feeds.
     * @return Updated newsfeed cache string.
     */
    private String insertPostIntoFeed(String feed, int post_id) {
        // TODO: Insert fancy algorithm here. For now just put it at the top of the feed and trim it if it's too big.
        String result;
        // Check how many items the feed currently has.
        if(feed.isEmpty()) {
            // Feed is empty, only insert the new post ID.
            result = String.valueOf(post_id);
        } else {
            // Feed is not empty, insert the new post ID with a delimiter.
            result = String.valueOf(post_id) + CachedItemDao.PAYLOAD_LIST_DELIMITER + feed;
        }
        return result;
    }
}
