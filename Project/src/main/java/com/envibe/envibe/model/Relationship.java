package com.envibe.envibe.model;



public class Relationship {

    private String userName;
    private String userFriend;

	public Relationship() {

    }
	
	public Relationship(String userName, String userFriend) {
        this.userName = userName;
        this.userFriend = userFriend;
    }
	
	public String getUserName()
    {
        return this.userName;
    }
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserFriend() {
		return this.userFriend;
	}
	
	public void setUserFriend(String userFriend) {
		this.userFriend = userFriend;
	}
}
