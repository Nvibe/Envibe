package com.envibe.envibe.dao;

import com.envibe.envibe.model.CachedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public class CachedItemDao {
    public static String PURPOSE_GENERAL_CACHE = "GENERAL";
    public static String PURPOSE_NEWS_FEED_CACHE = "NEWS";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void create(@NotNull CachedItem cachedItem) {
        redisTemplate.opsForValue().set(generateTag(cachedItem), cachedItem);
    }
    public CachedItem read(@NotNull String tag) {
        return (CachedItem)redisTemplate.opsForValue().get(tag);
    }
    public void update(@NotNull CachedItem cachedItem) {
        delete(generateTag(cachedItem));
        create(cachedItem);
    }
    public void delete(@NotNull CachedItem cachedItem) {
        delete(generateTag(cachedItem));
    }
    public void delete(@NotNull String tag) {
        redisTemplate.delete(tag);
    }
    private String generateTag(@NotNull String purpose, String user_tag) {
        return purpose + "|" + user_tag;
    }
    private String generateTag(@NotNull CachedItem cachedItem) {
        return generateTag(cachedItem.getPurpose(), cachedItem.getUserTag());
    }
}
