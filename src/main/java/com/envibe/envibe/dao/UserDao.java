package com.envibe.envibe.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import com.envibe.envibe.model.User;
import com.envibe.envibe.rowmapper.UserRowMapper;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
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
    private final String queryCreate = "INSERT INTO user_account (user_name, user_password, user_email, user_role, country, birthday, last_name, first_name, image_link) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Prepared query to search for user records in database.
     */
    private final String queryRead = "SELECT * FROM user_account " +
                                        "WHERE user_name = ? " +
                                        "LIMIT 1";

    private final String queryReadAll = "SELECT * FROM user_account";

    /**
     * Prepared query to update user records in database.
     */
    private final String queryUpdate = "UPDATE user_account " +
            "SET " +
                "user_password = ?, " +
                "user_email = ?, " +
                "user_role = ?, " +
                "country = ?, " +
                "birthday = ?, " +
                "last_name = ?, " +
                "first_name = ?, " +
                "image_link = ? " +
            "WHERE user_name = ?";

    /**
     * Prepared query to delete user records in database.
     */
    private final String queryDelete = "DELETE FROM user_account " +
                                        "WHERE user_name = ?";

    /**
     * Creates a pre-validated user in the permanent datastore.
     * @param user Pre-validated user model object to insert.
     */
    public void create(@Valid User user) {
        Objects.requireNonNull(user, "Method argument user cannot be null");
        jdbcTemplate.update(queryCreate, user.getUsername(), user.getPassword(), user.getEmail(), user.getRole(), user.getCountry(), user.getBirthday(), user.getLast_name(), user.getFirst_name(), user.getImage_link());
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
        jdbcTemplate.update(queryUpdate, user.getPassword(), user.getEmail(), user.getRole(), user.getCountry(), user.getBirthday(), user.getLast_name(), user.getFirst_name(), user.getImage_link(), user.getUsername());
    }

    /**
     * Permanently deletes the specified user record from the permanent datastore.
     * @param user User model object that represents the record to be deleted.
     */
    public void delete(@Valid User user) {
        Objects.requireNonNull(user, "Method argument user cannot be null");
        jdbcTemplate.update(queryDelete, user.getUsername());
    }

    public List<User> readAll() {
        try {
            return jdbcTemplate.query(queryReadAll, new UserRowMapper());
        } catch (Exception e) {
            return new ArrayList<User>();
        }
    }
}
