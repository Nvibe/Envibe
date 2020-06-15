package com.envibe.envibe.service;

import com.envibe.envibe.dao.UserDao;
import com.envibe.envibe.exception.UserAlreadyExistsException;
import com.envibe.envibe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {
    // Use our internal data access object to pull SQL data.
    @Autowired
    private UserDao userDao;

    // Creates a new user with given information.
    @Transactional
    public void registerNewUserAccount(User user) throws UserAlreadyExistsException {
        // Check if username is already in use.
        if (userDao.read(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username '" + user.getUsername() + "' already in use.");
        }

        // Hash the user's password and store it back in the user object.
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Commit user object to database.
        userDao.create(user);
    }
}
