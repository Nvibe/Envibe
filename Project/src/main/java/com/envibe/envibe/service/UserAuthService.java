package com.envibe.envibe.service;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.envibe.envibe.dao.UserDao;
import com.envibe.envibe.model.User;

/**
 * Custom authentication service that glues our user DAO implementation with the Spring Security framework login and session validation functions.
 * @see UserDao
 *
 * @author ARMmaster17
 */
@Service
public class UserAuthService implements UserDetailsService {

    /**
     * Injected data access object to query the user table against.
     */
    @Autowired
    private UserDao userDao;

    // Load a user by username to validate the current user session.

    /**
     * Load a user by username to validate the current user session.
     * @param username Username of account to tie validated session cookie with.
     * @return Internal Spring Security framework object to validate password hashes and role permissions against.
     * @throws UsernameNotFoundException If supplied username is not a valid, active user in the permanent datastore.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load the core user properties through the User model data access object.
        User user = userDao.read(username);
        // Throw an exception if the supplied username is not connected to any existing, active user account.
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        // Package the user's permission scopes to be consumed by the Spring Security framework.
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());

        // Send the packaged User model attributes to Spring Security framework for session validation.
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(grantedAuthority));
    }
}
