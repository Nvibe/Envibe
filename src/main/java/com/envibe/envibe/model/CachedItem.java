package com.envibe.envibe.model;

import java.io.Serializable;

/**
 * Base model for items stored in the application-wide cache (Redis in production).
 * @see com.envibe.envibe.dao.CachedItemDao
 *
 * @author ARMmaster17
 */
public class CachedItem implements Serializable {

    // TODO: Craete validator for this field since there are a limited number of valid values.
    /**
     * Describes the service that this item should be associated with. See the static fields of {@link com.envibe.envibe.dao.CachedItemDao} for valid values.
     */
    private String purpose;

    /**
     * The user account that this item should be associated with.
     */
    private String user_tag;

    /**
     * The payload of data to be stored. Can store any ISerializable or JSON object.
     * May change to be ISerializable instead of String depending on implementation of inherited classes.
     */
    private String payload;

    /**
     * Returns the service that this item is associated with. See the static fields of {@link com.envibe.envibe.dao.CachedItemDao} for valid values.
     * @return Internal purpose tag.
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Returns the username of the user account that this item is associated with.
     * @return Associated username.
     */
    public String getUserTag() {
        return user_tag;
    }
}
