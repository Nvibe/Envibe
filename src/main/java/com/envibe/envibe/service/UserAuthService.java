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

@Service
public class UserAuthService implements UserDetailsService {
    // Use our internal data access object to access SQL data.
    @Autowired
    private UserDao userDao;

    // Load a user by username to validate the current user session.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.read(username);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(grantedAuthority));
    }
}
