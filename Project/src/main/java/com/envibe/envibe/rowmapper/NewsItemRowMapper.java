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

    @Override
    public NewsItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new NewsItem(
                rs.getInt("post_id"),
                rs.getString("user_name"),
                rs.getTimestamp("post_date"),
                rs.getString("content")
        );
    }
}
