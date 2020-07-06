package com.envibe.envibe.dao;

import com.envibe.envibe.model.CachedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Data access object for CachedItems that are stored in a Redis-compatible datastore. Utilizes CRUD model.
 *
 * @author ARMmaster17
 */
@Repository
public class CachedItemDao {

    // TODO: Replace these strings with an enum.
    /**
     * Global tag for cached items that are not connected to a specific service.
     */
    public static final String PURPOSE_GENERAL_CACHE = "GENERAL";

    /**
     * Global tag for cached items that are produced and consumed by the news feed service.
     */
    public static final String PURPOSE_NEWS_FEED_CACHE = "NEWSFEEDCACHE";

    /**
     * Global tag for cached items that need to be passed from frontend to threaded workers.
     */
    public static final String PURPOSE_NEWS_FEED_WORKER_PASSTHROUGH = "NEWSFEEDWORKERPASSTHROUGH";

    /**
     * Injected Redis connection object to run queries against. See {@link RedisTemplate}.
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Creates a cached item using a generated tag.
     * @param cachedItem Item to store in the application-wide cache.
     */
    public void create(@NotNull CachedItem cachedItem) {
        // Argument validation.
        Objects.requireNonNull(cachedItem, "Method argument cachedItem cannot be null");
        // Commit record to cache.
        redisTemplate.opsForValue().set(generateTag(cachedItem), cachedItem);
    }

    /**
     * Finds a cache item with given key.
     * @param tag Access key for item. Usually follows the PURPOSE|USER schema. See {@link CachedItemDao#generateTag(String, String)}.
     * @return Selected cache item (if it exists).
     */
    public CachedItem read(@NotNull String tag) {
        // Argument validation.
        Objects.requireNonNull(tag, "Method argument tag cannot be null");
        // Return record from memory.
        // TODO: Throw custom exception if item does not exist.
        return (CachedItem)redisTemplate.opsForValue().get(tag);
    }

    /**
     * Finds a cache item with a given key and deletes the entry immediately before returning the found value. Reduces risk of duplicate threads
     * or race conditions or deadlock.
     * @param tag Access key for item. Usually follows the PURPOSE|USER schema. See {@link CachedItemDao#generateTag(String, String)}.
     * @return Selected cache item (if it exists). Is deleted before it is returned.
     */
    public CachedItem readAndDelete(@NotNull String tag) {
        // No argument validation. Handled by the inner classes.
        CachedItem item = this.read(tag);
        // Immediately delete the tag so no objects are retrieved.
        this.delete(tag);
        return item;
    }

    /**
     * Updates the given cache item in the application-wide cache.
     * @param cachedItem Valid CachedItem with updated payload. The purpose and user_tag fields must be the original values for the update to work.
     */
    public void update(@NotNull CachedItem cachedItem) {
        // Argument validation.
        Objects.requireNonNull(cachedItem, "Method argument cachedItem cannot be null");
        // Delete old record from cache.
        delete(generateTag(cachedItem));
        // Commit new record to cache.
        create(cachedItem);
    }

    /**
     * Removes the given cache item from the application-wide cache. Item must exist and contain the original purpose and user_tag values for the operation to be valid.
     * @param cachedItem Item to delete.
     */
    public void delete(@NotNull CachedItem cachedItem) {
        // Argument validation.
        Objects.requireNonNull(cachedItem, "Method argument cachedItem cannot be null");
        // Delete record from cache.
        // TODO: Throw a custom exception if the record does not exist.
        delete(generateTag(cachedItem));
    }

    /**
     * Removes a cache item from the application-wide cache with the given key.
     * @param tag Access key for item. Usually follows the PURPOSE|USER schema. See {@link CachedItemDao#generateTag(String, String)}.
     */
    public void delete(@NotNull String tag) {
        // Argument validation.
        Objects.requireNonNull(tag, "Method argument tag cannot be null");
        redisTemplate.delete(tag);
    }

    /**
     * Generates a searchable tag for a cache item.
     * @param purpose Service that this item should be attached to. See the static fields for valid values.
     * @param user_tag User that this item should be attached to.
     * @return Generated search tag that can be used for CRUD operations. Schema follows the PURPOSE|USER format.
     */
    public String generateTag(@NotNull String purpose, @NotNull String user_tag) {
        // Argument validation.
        Objects.requireNonNull(purpose, "Method argument purpose cannot be null");
        Objects.requireNonNull(user_tag, "Method argument user_tag cannot be null");
        return purpose + "|" + user_tag;
    }

    /**
     * Generates a searchable tag for a cache item.
     * @param cachedItem Object to pull parameters from to generate search tag.
     * @return Generated search tag that can be used for CRUD operations. Schema follows the PURPOSE|USER format.
     */
    private String generateTag(@NotNull CachedItem cachedItem) {
        // Argument validation.
        Objects.requireNonNull(cachedItem, "Method argument cachedItem cannot be null");
        return generateTag(cachedItem.getPurpose(), cachedItem.getUserTag());
    }
}
