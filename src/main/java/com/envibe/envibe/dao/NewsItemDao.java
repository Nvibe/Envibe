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
    final String queryCreate = "INSERT INTO newspost (user_name, post_date, post_content) VALUES (?, ?, ?)";

    /**
     * Prepared query to create a single post when the underlying database does not support auto-incrementing primary keys.
     */
    final String queryCreateNonAutoKey = "INSERT INTO newspost (post_id, user_name, post_date, post_content) VALUES (?, ?, ?, ?)";

    /**
     * Prepared query to find a single post by ID.
     */
    final String queryRead = "SELECT post_id, user_name, post_date, post_content FROM newspost WHERE post_id = ?";

    /**
     * Prepared query to find all posts created by a specified username.
     */
    final String queryReadByUsername = "SELECT post_id, user_name, post_date, post_content FROM newspost WHERE user_name = ?";

    /**
     * Prepared query to update a single post by ID.
     */
    final String queryUpdate = "UPDATE newspost SET user_name = ?, post_date = ?, post_content = ? WHERE post_id = ?";

    /**
     * Prepared query to delete a single post by ID.
     */
    final String queryDelete = "DELETE FROM newspost WHERE post_id = ?";

    /**
     * Prepared query to return the number of posts in the permanent datastore.
     */
    final String queryCountRows = "SELECT COUNT(*) FROM newspost";

    /**
     * Creates a pre-validated post in the permanent datastore. Also triggers the newsfeed update service.
     * @see NewsFeedUpdateService#triggerWorker(int)
     * @param newsItem Pre-validated post model object to insert.
     */
    public void create(@Valid NewsItem newsItem) {
        Objects.requireNonNull(newsItem, "Method argument newsItem cannot be null");
        int newId;
        if(System.getenv("JDBC_DATABASE_URL").contains("h2")) {
            newId = getNextId();
            jdbcTemplate.update(queryCreateNonAutoKey, newId, newsItem.getUsername(), newsItem.getPost_date(), newsItem.getContent());
        } else {
            newId = jdbcTemplate.update(queryCreate, newsItem.getUsername(), newsItem.getPost_date(), newsItem.getContent());
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
            return jdbcTemplate.queryForObject(queryRead, new String[]{Integer.toString(post_id)}, new NewsItemRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Searches for and returns a list of posts created by the specified user.
     * @param user_name Username to search for in list of post creators.
     * @return List of unsorted posts with specified username as the original author.
     */
    public List<NewsItem> read(@ValidUsername String user_name) {
        Objects.requireNonNull(user_name, "Method argument user_name cannot be null");
        // Create container to hold un-processed result set.
        List<Map<String, Object>> result;
        try {
            result = jdbcTemplate.queryForList(queryReadByUsername, new String[]{user_name});
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<NewsItem>();
        }
        // Create an instance of the DateFormat class for parsing dates from raw string input.
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        // Return a collected List<NewsItem> with a lambda function to individually process each row.
        return result.stream().map(m -> {
            // Create storage for post-processed post_date outside of try/catch scope.
            Date post_date;
            try {
                // Attempt to parse the value of column post_date for this row as a Date object.
                post_date = dateFormat.parse(String.valueOf(m.get("POST_DATE")));
            } catch (ParseException e) {
                // Should probably actually handle this exception.
                return null;
            }
            // Create a NewsItem object and return it so it can be included in the collected List<NewsItem>.
            NewsItem n = new NewsItem(
                    Integer.parseInt(String.valueOf(m.get("POST_ID"))),
                    String.valueOf(m.get("USER_NAME")),
                    post_date,
                    String.valueOf(m.get("POST_CONTENT"))
            );
            return n;
        }).collect(Collectors.toList());
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
}
