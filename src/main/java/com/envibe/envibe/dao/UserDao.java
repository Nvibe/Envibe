package com.envibe.envibe.dao;

import com.envibe.envibe.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import com.envibe.envibe.model.User;
import com.envibe.envibe.rowmapper.UserRowMapper;

@Repository
public class UserDao {
    // Pull in the JDBC access object from Spring.
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Create our prepared SQL statements as final Strings.
    final String queryCreate = "INSERT INTO user (user_name, user_pass, user_email, user_role) VALUES (?, ?, ?, ?)";
    final String queryRead = "SELECT user_name, user_pass, user_email, user_role FROM user WHERE user_name = ?";
    final String queryUpdate = "UPDATE user SET user_pass = ?, user_email = ?, user_role = ? WHERE user_name = ?";
    final String queryDelete = "DELETE FROM user WHERE user_name = ?";

    // CRUD definitions for User model.
    public void create(User user) {
        jdbcTemplate.update(queryCreate, user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
    }

    public User read(String username) {
        return jdbcTemplate.queryForObject(queryRead, new String[] { username }, new UserRowMapper());
    }

    public void update(User user) {
        jdbcTemplate.update(queryUpdate, user.getPassword(), user.getEmail(), user.getRole(), user.getUsername());
    }

    public void delete(User user) {
        jdbcTemplate.update(queryDelete, user.getUsername());
    }
}