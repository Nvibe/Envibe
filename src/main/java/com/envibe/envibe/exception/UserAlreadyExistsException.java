package com.envibe.envibe.exception;

/**
 * Internal exception thrown when a user with the specified attributes does not exist. See {@link com.envibe.envibe.dao.UserDao}.
 *
 * @author ARMmaster17
 */
public class UserAlreadyExistsException extends Exception {

    /**
     * Generic constructor that passes internal error message up the stack.
     * @param msg Internal error message.
     */
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
