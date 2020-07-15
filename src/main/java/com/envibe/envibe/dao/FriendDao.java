package com.envibe.envibe.dao;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.envibe.envibe.model.Relationship;
import com.envibe.envibe.rowmapper.FriendRowMapper;

@Repository
public class FriendDao {
	 @Autowired
	 /* injects JdbcTemplate into class*/
	private JdbcTemplate jdbcTemplate;
	
	final String queryCreate = "INSERT INTO user_relationship (user_name, user_friend) VALUES (?, ?)";
	
	final String queryRead = "SELECT user_friend FROM user_relationship WHERE user_name = ?";
	
	final String queryUpdate = "UPDATE user_relationship SET user_name = ?, user_friend = ? WHERE user_name = ?";
	
	final String queryDelete = "DELETE FROM user_relationship WHERE user_name = ?";
	
	/*General CRUD format, update not actually necessary but for "Future Features" and the possibility of needing it in case users update their userNames than its just there*/
	
	public void create(Relationship relation) {
        Objects.requireNonNull(relation, "Method argument relation cannot be null");
        jdbcTemplate.update(queryCreate, relation.getUserName(), relation.getUserFriend());
    }
	
	/*Creates a list of objects of Relationship.*/
	public List<Relationship> read(@NotNull String userName) {
        Objects.requireNonNull(userName, "Method argument userName cannot be null");
        try {
        	return jdbcTemplate.query(queryRead, new FriendRowMapper(), userName);
        } catch (Exception e) {
            return null;
        }
    }
	
	/*Unused but could be eventually.*/
	public void update(Relationship relation) {
        Objects.requireNonNull(relation, "Method argument user cannot be null");
        jdbcTemplate.update(queryUpdate, relation.getUserName(), relation.getUserFriend());
    }
	
	/*Used to delete record from database, call a relationship object and than delete it in query.*/
	public void delete(Relationship relation) {
        Objects.requireNonNull(relation, "Method argument relation cannot be null");
        jdbcTemplate.update(queryDelete, relation.getUserName());
    }
}
