package com.envibe.envibe.rowmapper;

import com.envibe.envibe.model.NewsItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * Handles mapping internal database columns to attributes inside NewsItem model.
 * @see com.envibe.envibe.model.NewsItem
 * @see com.envibe.envibe.dao.NewsItemDao
 *
 * @author ARMmaster17
 */
public class NewsItemRowMapper implements RowMapper<NewsItem> {

    /**
     * Maps the internal structure of the database with the private members of the NewsItem model.
     * @param rs Raw SQL result.
     * @param rowNum Row number to use. (Not used)
     * @return A NewsItem model populated with the values from the permanent datastore.
     * @throws SQLException If table is not initialized correctly or an invalid value is found.
     */
    @Override
    public NewsItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new NewsItem(
                rs.getInt("post_id"),
                rs.getString("user_name"),
                rs.getTimestamp("post_date"),
                rs.getString("post_content")
        );
    }
}
