package com.envibe.envibe.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.envibe.envibe.model.User;

public class UserRowMapper implements RowMapper<User> {
    // Maps our internal SQL table layout to a valid User object.
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getString("user_name"),
                rs.getString("user_password"),
                rs.getString("user_role"),
                rs.getString("user_email"));
    }
}
