package com.envibe.envibe.dao;

import com.envibe.envibe.model.NewsItem;
import com.envibe.envibe.model.validation.constraints.ValidUsername;
import com.envibe.envibe.rowmapper.NewsItemRowMapper;
import com.envibe.envibe.service.NewsFeedUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Data access object for news items that are stored in a permanent JDBC-compatible datastore. Utilizes CRUD model.
 *
 * @author ARMmaster17
 */
@Repository
public class NewsItemDao {

    /**
     * Injected JDBC connection object to run queries against. See {@link JdbcTemplate}.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Injected service that handles background workers that update the news feed caches when a new post is created. See {@link NewsFeedUpdateService}.
     */
    @Autowired
    private NewsFeedUpdateService newsFeedUpdateService;

    /**
     * Prepared query to create a single post.
     */
    private static final String queryCreate = "INSERT INTO newspost (user_name, post_date, post_content) " +
                                                "VALUES (?, ?, ?)";

    /**
     * Prepared query to create a single post when the underlying database does not support auto-incrementing primary keys.
     */
    final String queryCreateNonAutoKey = "INSERT INTO newspost (post_id, user_name, post_date, post_content) " +
                                            "VALUES (?, ?, ?, ?)";

    /**
     * Prepared query to find a single post by ID.
     */
    private static final String queryRead = "SELECT post_id, user_name, post_date, post_content " +
                                            "FROM newspost " +
                                            "WHERE post_id = ? " +
                                            "LIMIT 1";

    /**
     * Prepared query to find all posts created by a specified username.
     */
    private static final String queryReadByUsername = "SELECT post_id, user_name, post_date, post_content " +
                                                        "FROM newspost " +
                                                        "WHERE user_name = ? " +
                                                        "LIMIT ?";

    /**
     * Prepared query to update a single post by ID.
     */
    private static final String queryUpdate = "UPDATE newspost " +
                                                "SET " +
                                                    "user_name = ?, " +
                                                    "post_date = ?, " +
                                                    "post_content = ? " +
                                                "WHERE post_id = ?";

    /**
     * Prepared query to delete a single post by ID.
     */
    private static final String queryDelete = "DELETE FROM newspost " +
                                                "WHERE post_id = ?";

    /**
     * Prepared query to return the number of posts in the permanent datastore.
     */
    final String queryCountRows = "SELECT COUNT(*) FROM newspost";

    /**
     * Default number of posts to return in a query that returns more than one record.
     */
    private static final int DEFAULT_POST_COUNT = 10;

    /**
     * Creates a pre-validated post in the permanent datastore. Also triggers the newsfeed update service.
     * @see NewsFeedUpdateService#triggerWorker(int)
     * @param newsItem Pre-validated post model object to insert.
     */
    public void create(@Valid NewsItem newsItem) {
        Objects.requireNonNull(newsItem, "Method argument newsItem cannot be null");
        int newId;
        if(supportsSerialPrimaryKeys()) {
            newId = jdbcTemplate.update(queryCreate, newsItem.getUsername(), newsItem.getPost_date(), newsItem.getContent());
        } else {
            newId = getNextId();
            jdbcTemplate.update(queryCreateNonAutoKey, newId, newsItem.getUsername(), newsItem.getPost_date(), newsItem.getContent());
        }
        newsItem.setPost_id(newId);
        // Fire off the NewsFeedUpdaterService to add the post to the user's friend's newsfeeds.
        newsFeedUpdateService.triggerWorker(newsItem.getPost_id());
    }

    /**
     * Searches for and returns a post with a matching ID.
     * @param post_id Post to search for.
     * @return NewsItem model object if the specified ID exists. Otherwise returns null.
     */
    public NewsItem read(@NotNull int post_id) {
        Objects.requireNonNull(post_id, "Method argument post_id cannot be null");
        try {
            return jdbcTemplate.queryForObject(queryRead, new NewsItemRowMapper(), Integer.toString(post_id));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Batch call that searches for and returns posts with matching IDs.
     * @param post_ids List of IDs to look for.
     * @return NewsItem model objects if their corresponding IDs exist. Otherwise returns null.
     */
    public List<NewsItem> read(int... post_ids) {
        // Apparently batch SELECT calls are not allowed. This will just be a wrapper method.
        ArrayList<NewsItem> results = new ArrayList<>();
        for (int post_id : post_ids) {
            results.add(read(post_id));
        }
        return results;
    }

    /**
     * Searches for and returns a list of posts created by the specified user.
     * @param user_name Username to search for in list of post creators.
     * @return List of unsorted posts with specified username as the original author.
     */
    public List<NewsItem> read(@ValidUsername String user_name) {
        return read(user_name, DEFAULT_POST_COUNT);
    }

    /**
     * Searches for and returns a list of posts created by the specified user.
     * @param user_name Username to search for in list of post creators.
     * @param count Number of posts to return.
     * @return List of unsorted posts with specified username as the original author.
     */
    public List<NewsItem> read(@ValidUsername String user_name, int count) {
        Objects.requireNonNull(user_name, "Method argument user_name cannot be null");
        try {
            return jdbcTemplate.query(queryReadByUsername, new NewsItemRowMapper(), user_name, String.valueOf(count));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<NewsItem>();
        }
    }

    /**
     * Updates the attributes of the specified NewsItem model using post_id to find original record.
     * @param newsItem Updated NewsItem model object to replace original.
     */
    public void update(@Valid NewsItem newsItem) {
        Objects.requireNonNull(newsItem, "Method argument newsItem cannot be null");
        jdbcTemplate.update(queryUpdate, newsItem.getUsername(), newsItem.getPost_date(), newsItem.getContent(), newsItem.getPost_id());
    }

    /**
     * Deletes the specified valid NewsItem from the permanent datastore.
     * @param newsItem NewsItem model object to delete, searched by post_id.
     */
    public void delete(@Valid NewsItem newsItem) {
        Objects.requireNonNull(newsItem, "Method argument newsItem cannot be null");
        Objects.requireNonNull(newsItem.getPost_id(), "Object attribute newsItem.post_id cannot be null");
        jdbcTemplate.update(queryDelete, newsItem.getPost_id());
    }

    /**
     * Gets the next ID to use for a post. Used only with databases that don't support the SERIAL datatype.
     * @return Next available value to use for post_id.
     */
    private int getNextId() {
        return jdbcTemplate.queryForObject(queryCountRows, Integer.class);
    }

    /**
     * Checks if a JDBC permanent datastore is being used that supports auto-incrementing keys.
     * @return If the current JDBC connection supports the SERIAL keyword.
     */
    private boolean supportsSerialPrimaryKeys() {
        return !System.getenv("JDBC_DATABASE_URL").contains("h2");
    }
}
