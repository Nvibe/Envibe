package com.envibe.envibe.dto;

import java.io.Serializable;
import java.util.Date;

public class NewsFeedItemDto implements Serializable {
    private int post_id;
    private String username;
    private Date post_date;
    private String content;
    private String user_image;
    public NewsFeedItemDto() {

    }
    public NewsFeedItemDto(String username, Date post_date, String content, String user_image) {
        this.username = username;
        this.post_date = post_date;
        this.content = content;
        this.user_image = user_image;
    }
    public NewsFeedItemDto(int post_id, String username, Date post_date, String content, String user_image) {
        this.post_id = post_id;
        this.username = username;
        this.post_date = post_date;
        this.content = content;
        this.user_image = user_image;
    }
    public int getPost_id(){return post_id;}
    public void setPost_id(int post_id) {this.post_id = post_id;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public Date getPost_date() {return post_date;}
    public  void setPost_date(Date post_date) {this.post_date = post_date;}
    public String getContent() { return content;}
    public void setContent(String content) { this.content = content;}
    public String getUser_image() {return user_image;}
    public void setUser_image(String user_image) {this.user_image = user_image;}
}
