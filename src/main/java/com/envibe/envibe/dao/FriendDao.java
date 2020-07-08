package com.envibe.envibe.dao;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.envibe.envibe.model.Relationship;
import com.envibe.envibe.rowmapper.FriendRowMapper;

public class FriendDao {
	private JdbcTemplate jdbcTemplate;
	
	final String queryCreate = "INSERT INTO user_relationship (user_name, user_friend) VALUES (?, ?)";
	
	final String queryRead = "SELECT user_friend FROM user_relationship WHERE user_name = ?";
	
	final String queryUpdate = "UPDATE user_relationship SET user_name = ?, user_friend = ? WHERE user_name = ?";
	
	final String queryDelete = "DELETE FROM user_relationship WHERE user_name = ?";
	
	public void create(Relationship relation) {
        Objects.requireNonNull(relation, "Method argument relation cannot be null");
        jdbcTemplate.update(queryCreate, relation.getUserName(), relation.getUserFriend());
    }
	
	public List<String[]> read(String userName) {
        Objects.requireNonNull(userName, "Method argument userName cannot be null");
        try {
            return jdbcTemplate.queryForList(queryRead, String[]{userName}, new FriendRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
	
	public void update(Relationship relation) {
        Objects.requireNonNull(relation, "Method argument user cannot be null");
        try {
        jdbcTemplate.update(queryUpdate, relation.getUserName(), relation.getUserFriend());
        } catch (Exception e) {
        	return;
        }
    }
	
	public void delete(Relationship relation) {
        Objects.requireNonNull(relation, "Method argument relation cannot be null");
        jdbcTemplate.update(queryDelete, relation.getUserName());
    }
}
