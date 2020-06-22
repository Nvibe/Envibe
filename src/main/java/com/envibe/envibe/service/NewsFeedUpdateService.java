package com.envibe.envibe.service;

import com.envibe.envibe.dao.CachedItemDao;
import com.envibe.envibe.model.CachedItem;
import com.envibe.envibe.worker.NewsFeedUpdateWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Handles background updating of friend's news feed when a user creates a post or an event triggers an update on a user's activity.
 * All actions run in the background on the same server that called the service. Trust me, RabbitMQ is not worth it on this project.
 * @see com.envibe.envibe.worker.NewsFeedUpdateWorker
 *
 * @author ARMmaster17
 */
@Service
public class NewsFeedUpdateService {

    /**
     * Injected data access object for CachedItems in the temporary datastore.
     */
    @Autowired
    CachedItemDao cachedItemDao;

    /**
     * Local pool of workers so that we can invoke their control functions if needed.
     */
    private ArrayList<Thread> workers;

    /**
     * Called as Spring starts up. Initializes worker pool.
     */
    @PostConstruct
    private void init() {
        workers = new ArrayList<Thread>();
    }

    /**
     * Creates a worker, assigns it a post_id, and adds it to the thread pool.
     * @param post_id
     */
    public void triggerWorker(int post_id) {
        // Send a payload through the temporary datastore with the new post ID to add to the newsfeed.
        CachedItem newPostMessage = new CachedItem(CachedItemDao.PURPOSE_NEWS_FEED_WORKER_PASSTHROUGH, "INTERNAL", Integer.toString(post_id));
        // Save the payload so that it can be accessed by the worker thread.
        cachedItemDao.create(newPostMessage);
        // Fire off a worker and assign it to the worker pool so we can monitor it.
        workers.add(new Thread(new NewsFeedUpdateWorker()));
    }

    /**
     * Called on Spring shutdown. Forcefully kills all workers in the pool.
     */
    @PreDestroy
    public void dismantle() {
        for(Thread worker : workers) {
            worker.stop();
        }
    }
}
