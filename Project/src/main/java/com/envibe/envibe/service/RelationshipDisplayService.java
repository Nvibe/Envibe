package com.envibe.envibe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.envibe.envibe.dao.FriendDao;
import com.envibe.envibe.dao.UserDao;
import com.envibe.envibe.model.User;

public class RelationshipDisplayService {
	 @Autowired
	private UserDao UserDao;
	private FriendDao FriendDao;
	
	public  FriendsList(String user_name) {
		User u = UserDao.read(user_name);
		FriendDao.read(u.getUsername());
	}
}
