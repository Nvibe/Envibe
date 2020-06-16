package com.envibe.envibe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.envibe.envibe.service.UserAuthService;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Manages the application-specific security settings for the Spring Security framework. Handles password hashing, defining authenticated endpoints, and how to handle POST login requests.
 *
 * @author ARMmaster17
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Injected dependency on our own authentication service that handles searching for users. See {@link UserAuthService}.
     */
    @Autowired
    private UserAuthService userAuthService;

    /**
     * Connects our own authentication service with the password hashing method of our choice.
     * @param auth Injected builder service given by the Spring Security framework.
     * @throws Exception Usually thrown by underlying Spring Security framework or a missing system package for the selected password hashing service.
     * @see SecurityConfig#passwordEncoder()
     * @see UserAuthService
     */
    @Autowired
    public void configureGlobal(@NotNull AuthenticationManagerBuilder auth) throws Exception {
        // Argument validation.
        Objects.requireNonNull(auth, "Method argument auth cannot be null");
        // Bind our custom authentication service to the globally-selected password encoder class.
        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
    }

    /**
     * Configures the authentication-related settings of the web service. Defines endpoints that do not require a valid session cookie.
     * @param http Injected object representing configuration options of the Spring Security framework.
     * @throws Exception Usually thrown by underlying Spring Security framework. Possibly bad Regex expression for endpoint definitions.
     */
    @Override
    protected void configure(@NotNull HttpSecurity http) throws Exception {
        // Argument validation.
        Objects.requireNonNull(http, "Method argument http cannot be null");
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

    /**
     * Defines the password hashing service that will be used internally by the Spring Security framework.
     * @return Encoding service of our choice (in this case, BCrypt).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCrypt since it's widely available on Linux, secure, and "it just works".
        return new BCryptPasswordEncoder();
    }
}
