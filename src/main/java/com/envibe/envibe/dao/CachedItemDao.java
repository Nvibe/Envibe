package com.envibe.envibe.dao;

import com.envibe.envibe.model.CachedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.NotNull;

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
    public static String PURPOSE_GENERAL_CACHE = "GENERAL";

    /**
     * Global tag for cached items that are produced and consumed by the news feed service.
     */
    public static String PURPOSE_NEWS_FEED_CACHE = "NEWS";

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
        redisTemplate.opsForValue().set(generateTag(cachedItem), cachedItem);
    }

    /**
     * Finds a cache item with given key.
     * @param tag Access key for item. Usually follows the PURPOSE|USER schema. See {@link CachedItemDao#generateTag(String, String)}.
     * @return Selected cache item (if it exists).
     */
    public CachedItem read(@NotNull String tag) {
        return (CachedItem)redisTemplate.opsForValue().get(tag);
    }

    /**
     * Updates the given cache item in the application-wide cache.
     * @param cachedItem Valid CachedItem with updated payload. The purpose and user_tag fields must be the original values for the update to work.
     */
    public void update(@NotNull CachedItem cachedItem) {
        delete(generateTag(cachedItem));
        create(cachedItem);
    }

    /**
     * Removes the given cache item from the application-wide cache. Item must exist and contain the original purpose and user_tag values for the operation to be valid.
     * @param cachedItem Item to delete.
     */
    public void delete(@NotNull CachedItem cachedItem) {
        delete(generateTag(cachedItem));
    }

    /**
     * Removes a cache item from the application-wide cache with the given key.
     * @param tag Access key for item. Usually follows the PURPOSE|USER schema. See {@link CachedItemDao#generateTag(String, String)}.
     */
    public void delete(@NotNull String tag) {
        redisTemplate.delete(tag);
    }

    /**
     * Generates a searchable tag for a cache item.
     * @param purpose Service that this item should be attached to. See the static fields for valid values.
     * @param user_tag User that this item should be attached to.
     * @return Generated search tag that can be used for CRUD operations. Schema follows the PURPOSE|USER format.
     */
    private String generateTag(@NotNull String purpose, String user_tag) {
        return purpose + "|" + user_tag;
    }

    /**
     * Generates a searchable tag for a cache item.
     * @param cachedItem Object to pull parameters from to generate search tag.
     * @return Generated search tag that can be used for CRUD operations. Schema follows the PURPOSE|USER format.
     */
    private String generateTag(@NotNull CachedItem cachedItem) {
        return generateTag(cachedItem.getPurpose(), cachedItem.getUserTag());
    }
}
