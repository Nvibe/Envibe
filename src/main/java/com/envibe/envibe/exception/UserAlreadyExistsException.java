package com.envibe.envibe.exception;

// Exception to throw when we attempt to create a user, but they already exist in the database (username/email match).
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
