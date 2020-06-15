package com.envibe.envibe.model;

import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;

public class CachedItem implements Serializable {
    private String purpose;
    private String user_tag;
    private String payload;

    public String getPurpose() {
        return purpose;
    }

    public String getUserTag() {
        return user_tag;
    }
}
