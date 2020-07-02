package com.envibe.envibe.model;

import com.envibe.envibe.dao.CachedItemDao;

import java.io.Serializable;

/**
 * Base model for items stored in the application-wide cache (Redis in production).
 * @see com.envibe.envibe.dao.CachedItemDao
 *
 * @author ARMmaster17
 */
public class CachedItem implements Serializable {

    // TODO: Creaete validator for this field since there are a limited number of valid values.
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
     * Default constructor.
     */
    public CachedItem() {

    }

    /**
     * Overloaded constructor. Allows setting pre-generated parameters.
     * @param pregeneratedTag Generated tag for use in keystore.
     * @param payload Raw String payload to hold for external processing.
     */
    public CachedItem(String pregeneratedTag, String payload) {
        String[] options = pregeneratedTag.split(CachedItemDao.TAG_SPLITTER, 2);
        this.purpose = options[0];
        this.user_tag = options[1];
        this.payload = payload;
    }

    /**
     * Overloaded constructor. Allows setting all parameters.
     * @param purpose Service tag associated with this item. See the static final members of this class.
     * @param user_tag Username to tag this item with.
     * @param payload Raw String payload to hold for external processing.
     */
    public CachedItem(String purpose, String user_tag, String payload) {
        this.purpose = purpose;
        this.user_tag = user_tag;
        this.payload = payload;
    }

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

    /**
     * Returns the raw String payload.
     * @return
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets the payload to a raw String value for external parsing.
     * @param payload String-formatted payload.
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }
}
