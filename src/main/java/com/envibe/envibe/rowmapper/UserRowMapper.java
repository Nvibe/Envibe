package com.envibe.envibe.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.envibe.envibe.model.User;

/**
 * Handles mapping internal database columns to attributes inside User model.
 * @see User
 *
 * @author ARMmaster17
 */
public class UserRowMapper implements RowMapper<User> {

    /**
     * Maps raw SQL result set to a User model object.
     * @param rs Raw SQL result set.
     * @param rowNum Which row to pull data from.
     * @return Pre-filled User model object.
     * @throws SQLException If there is a column mis-match, an invalid row number is supplied, or the raw SQL result set is empty.
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getString("user_name"),
                rs.getString("user_password"),
                rs.getString("user_role"),
                rs.getString("user_email"));
    }
}
