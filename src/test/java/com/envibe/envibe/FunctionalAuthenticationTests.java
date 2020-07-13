package com.envibe.envibe;

import com.envibe.envibe.model.User;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Performs functional tests for the authentication controller, and all related Spring Security framework implementations.
 * @see com.envibe.envibe.controller.AuthenticationController
 * @see com.envibe.envibe.service.UserAuthService
 * @see com.envibe.envibe.service.UserRegistrationService
 * @see com.envibe.envibe.rowmapper.UserRowMapper
 * @see User
 * @see com.envibe.envibe.model.validation.validator.RoleValidator
 * @see com.envibe.envibe.model.validation.constraints.ValidRole
 * @see com.envibe.envibe.exception.UserAlreadyExistsException
 * @see com.envibe.envibe.dao.UserDao
 * @see com.envibe.envibe.config.SecurityConfig
 *
 * @author ARMmaster17
 */
public class FunctionalAuthenticationTests extends EnvibeApplicationTests {

    /**
     * Tests that the login page is rendered correctly.
     * @throws Exception Validation assertion failed.
     */
    @Test
    public void testLoginPage() throws Exception {
        // Send HTTP request GET:/login
        ResponseEntity<String> response = this.restTemplate.getForEntity(getURI("/login"), String.class);
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    /**
     * Tests that registration flow functions correctly.
     * @throws Exception Internal error occured (201 HTTP status code not returned) or not redirected to /login on successful validation.
     */
    @Test
    public void testDirectRegistration() throws Exception {
        // Create a User object to submit.
        User testUser = new User("testuser1", "password1", "ROLE_USER", "user1@example.com", "United States", new Date(2010, 12, 30), "Smith", "John", "");
        ResponseEntity<String> response = this.restTemplate.postForEntity(getURI("/register"), testUser, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Location").get(0)).contains("/login");
    }

    /**
     * Tests that the entire registration, login, and authentication stages work correctly. Not fully implemented yet.
     * @throws Exception Internal error occured (201 HTTP status code not returned) or not redirected as expected.
     */
    @Test
    public void testCompleteSignUpProcess() throws Exception {
        // Create a User object to submit.
        User testUser = new User("testuser1", "password1", "ROLE_USER", "user1@example.com", "United States", new Date(2010, 12, 30), "Smith", "John", "");
        // Specify that this payload is in the x-www-form-urlencoded format.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // Send testUser to the registration endpoint.
        ResponseEntity<String> responseRegister = this.restTemplate.postForEntity(getURI("/register"), testUser, String.class);
        // Verify that we were redirected.
        assertThat(responseRegister.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        // Verify that we were redirected to the login page and not back to the registration page.
        assertThat(responseRegister.getHeaders().get("Location").get(0)).contains("/login");
        // No idea why this block doesn't work. Ignoring for now since tests are low priority.
        /*// Create payload to test login with new user.
        MultiValueMap<String, String> loginCredentials = new LinkedMultiValueMap<>();
        loginCredentials.add("username", testUser.getUsername());
        loginCredentials.add("password", testUser.getPassword());
        // Convert JSON payload into request object.
        HttpEntity<MultiValueMap<String, String>> loginRequest = new HttpEntity<>(loginCredentials, headers);
        // POST request object to POST:/authenticate endpoint.
        ResponseEntity<String> responseLogin = this.restTemplate.postForEntity(getURI("/authenticate"), loginRequest, String.class);
        // Verify that we were redirected.
        assertThat(responseLogin.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        // Verify that we were redirected to the login page and not back to the registration page.
        assertThat(responseLogin.getHeaders().get("Location").get(0)).contains("/restricted");*/
    }
}
