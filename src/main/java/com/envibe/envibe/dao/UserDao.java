package com.envibe.envibe.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.envibe.envibe.model.User;
import com.envibe.envibe.rowmapper.UserRowMapper;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Data access object for Users that are stored in a permanent JDBC-compatible datastore. Utilizes CRUD model.
 *
 * @author ARMmaster17
 */
@Repository
public class UserDao {

    /**
     * Injected JDBC connection object to run queries against. See {@link JdbcTemplate}.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Prepared query to insert user records into database.
     */
    final String queryCreate = "INSERT INTO user_account (user_name, user_password, user_email, user_role) VALUES (?, ?, ?, ?)";

    /**
     * Prepared query to search for user records in database.
     */
    final String queryRead = "SELECT user_name, user_password, user_email, user_role FROM user_account WHERE user_name = ?";

    /**
     * Prepared query to update user records in database.
     */
    final String queryUpdate = "UPDATE user_account SET user_password = ?, user_email = ?, user_role = ? WHERE user_name = ?";

    /**
     * Prepared query to delete user records in database.
     */
    final String queryDelete = "DELETE FROM user_account WHERE user_name = ?";

    // TODO: Switch from Strings to PreparedSQLQuery objects.

    /**
     * Creates a pre-validated user in the permanent datastore.
     * @param user Pre-validated user model object to insert.
     */
    public void create(@Valid User user) {
        Objects.requireNonNull(user, "Method argument user cannot be null");
        jdbcTemplate.update(queryCreate, user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
    }

    /**
     * Searches for a user record with given username.
     * @param username Username to search for.
     * @return User model object if the specified username exists. Otherwise returns null.
     */
    public User read(@NotNull String username) {
        Objects.requireNonNull(username, "Method argument username cannot be null");
        try {
            return jdbcTemplate.queryForObject(queryRead, new String[]{username}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Updates the core account information for the specified user. Note that the username must be the same as the original record.
     * @param user Pre-validated user model object to replace existing record in permanent datastore.
     */
    public void update(@Valid User user) {
        Objects.requireNonNull(user, "Method argument user cannot be null");
        // TODO: Catch EmptyResultDataAccessExceptions.
        jdbcTemplate.update(queryUpdate, user.getPassword(), user.getEmail(), user.getRole(), user.getUsername());
    }

    /**
     * Permanently deletes the specified user record from the permanent datastore.
     * @param user User model object that represents the record to be deleted.
     */
    public void delete(@Valid User user) {
        Objects.requireNonNull(user, "Method argument user cannot be null");
        jdbcTemplate.update(queryDelete, user.getUsername());
    }
}
