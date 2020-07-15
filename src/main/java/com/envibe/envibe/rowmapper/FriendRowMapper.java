package com.envibe.envibe.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.envibe.envibe.model.Relationship;


public class FriendRowMapper implements RowMapper<Relationship>{
	
	
	/*Maps users and friends for SQL on Relationship model*/
	public Relationship mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Relationship(
                rs.getString("user_name"),
                rs.getString("user_friend"));
    }

}
