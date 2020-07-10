package com.envibe.envibe.model;



public class Relationship {
	
	/*Creates parameters for internal use of users and friends*/
    private String userName;
    private String userFriend;

    /*Empty initialization of constructor*/
	public Relationship() {

    }
	
	/*Constructor for Relationship model*/
	public Relationship(String userName, String userFriend) {
        this.userName = userName;
        this.userFriend = userFriend;
    }
	
	
	/*Simple getters and setters for model*/
	
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
