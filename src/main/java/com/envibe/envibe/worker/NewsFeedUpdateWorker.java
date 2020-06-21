package com.envibe.envibe.worker;

import com.envibe.envibe.dao.CachedItemDao;
import com.envibe.envibe.dao.NewsItemDao;
import com.envibe.envibe.dao.UserDao;
import com.envibe.envibe.model.CachedItem;
import com.envibe.envibe.model.NewsItem;
import com.envibe.envibe.model.User;
import com.envibe.envibe.service.NewsFeedUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Handles the updating of the news feed in the background whenever a new post is created. Launched by {@link NewsFeedUpdateService}.
 * @see com.envibe.envibe.service.NewsFeedUpdateService
 *
 * @author ARMmaster17
 */
@Component
public class NewsFeedUpdateWorker implements Runnable {

    /**
     * Stores the id of the new post until the Thread super class invokes the {@link NewsFeedUpdateWorker#run()} function.
     */
    private int post_id;

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

    /**
     * Override for constructor that accepts a post_id parameter to limit the update scope.
     * @param post_id ID of new NewsItem.
     */
    public NewsFeedUpdateWorker(int post_id) {
        this.post_id = post_id;
    }

    /**
     * Triggered by super Thread class. Runs the background task until completion, then the Thread super class performs automated garbage collection.
     */
    public void run() {
        // Pull the full post details from the database.
        NewsItem originalPost = newsItemDao.read(post_id);
        // Pull the entire profile of the user who created the post.
        User originalPoster = userDao.read(originalPost.getUsername());
        // Get a list of all the friends of the original user. Use Strings since we don't need any friend User attributes.
        ArrayList<String> friends = new ArrayList<String>();
        // TODO: Generate a list of friends of the OP.
        // Get the news feed caches for each friend.
        ArrayList<CachedItem> caches = new ArrayList<CachedItem>();
        // Use an index to keep both friends and caches in the same order.
        for (int i = 0; i < friends.size(); i++) {
            // Generate a tag from our service name and the friend's username.
            String tag = cachedItemDao.generateTag(CachedItemDao.PURPOSE_NEWS_FEED_CACHE, friends.get(i));
            // Load the cache (if it exists) into the same index in caches.
            caches.set(i, cachedItemDao.read(tag));
        }
        // Insert the post into the newsfeeds.
        // TODO: Insert fancy algorithm here. For now just put it at the top of the feed and trim it if it's too big.
        for (int i = 0; i < friends.size(); i++) {
            CachedItem cachedFeed;
            if(caches.get(i) == null) {
                // User does not have a newsfeed cache. Let's build one. For now we will only include the current post.
                caches.set(i, new CachedItem(CachedItemDao.PURPOSE_NEWS_FEED_CACHE, friends.get(i), Integer.toString(post_id)));
                // Commit the new cached item to the permanent datastore.
                cachedItemDao.create(caches.get(i));
            } else {
                // Cache does exist for this user. Modify the payload by putting the post_id at the front.
                String oldPayload = caches.get(i).getPayload();
                String newPayload = Integer.toString(post_id) + "," + oldPayload;
                caches.get(i).setPayload(newPayload);
                // Commit the changed cached item to the permanent datastore.
                cachedItemDao.update(caches.get(i));
            }
        }
    }
}
