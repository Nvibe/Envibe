package com.envibe.envibe.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.envibe.envibe.dao.FriendDao;
import com.envibe.envibe.model.Relationship;
@Service
public class RelationshipDisplayService {
	 @Autowired
	private FriendDao friendDao;
	
	private List<String> friendGroup;
	
	 /*Creates a list of String that pulls .getUserFriend from Relationship objects*/
	public List<String> FriendsList (String user_name) {
		List<Relationship> currentFriends = friendDao.read(user_name);
		if (currentFriends == null) return new ArrayList<String>();
		ArrayList<String> friendGroup = new ArrayList<>();
		for (Relationship j : currentFriends)
		{
			friendGroup.add(j.getUserFriend());
		}
		return friendGroup;
	}
}