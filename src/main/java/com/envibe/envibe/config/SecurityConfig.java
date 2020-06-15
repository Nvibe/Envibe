package com.envibe.envibe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.envibe.envibe.service.UserAuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Plug in our own user authentication service using JDBC.
    @Autowired
    private UserAuthService userAuthService;

    // Specify that we are creating a user service with default password hashing methods.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configure global security parameters of the http object.
        http
                // Must have valid session cookie to access site.
                .authorizeRequests()
                // The following endpoints do not require authorization.
                .antMatchers("/", "/login", "/register")
                .permitAll()
                .anyRequest()
                .authenticated()
            // Start a new rule.
            .and()
                // Authentication will be provided through an HTML form.
                .formLogin()
                .loginPage("/login")
                // Send users to this endpoint for processing after providing login credentials.
                .loginProcessingUrl("/authenticate")
                // After successful login, redirect users here.
                // TODO: Replace with user homepage once that is built.
                .defaultSuccessUrl("/restricted")
                // On authentication error, redirect them here.
                .failureUrl("/login?error")
                // Manually specify parameters to look for at POST:/authenticate.
                .usernameParameter("username")
                .passwordParameter("password");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCrypt since it's widely available on Linux, secure, and "it just works".
        return new BCryptPasswordEncoder();
    }
}
