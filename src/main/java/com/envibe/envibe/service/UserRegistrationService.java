package com.envibe.envibe.service;

import com.envibe.envibe.dao.UserDao;
import com.envibe.envibe.exception.UserAlreadyExistsException;
import com.envibe.envibe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles validation and creation of user accounts. Performs hashing operations using
 * @see UserDao
 *
 * @author ARMmaster17
 */
@Service
public class UserRegistrationService {

    /**
     * Injected data access object to run User table queries against.
     */
    @Autowired
    private UserDao userDao;

    /**
     * Validates, hashes, and stores new user account in the configured permanent data store.
     * @param user Pre-hashed unvalidated user model to store.
     * @throws UserAlreadyExistsException If the given username is not globally-unique and already exists in the permanent data store.
     */
    @Transactional
    public void registerNewUserAccount(User user) throws UserAlreadyExistsException {
        // Check if username is already in use.
        if (userDao.read(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username '" + user.getUsername() + "' already in use.");
        }

        // TODO: autowire with global type definition of configured password encoder.
        // Hash the user's password and store it back in the user object.
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Commit user object to database.
        userDao.create(user);
    }
}
