package com.envibe.envibe.unittest;

import com.envibe.envibe.controller.AuthenticationController;
import com.envibe.envibe.exception.UserAlreadyExistsException;
import com.envibe.envibe.model.User;
import com.envibe.envibe.service.UserRegistrationService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthenticationControllerUnitTest {
    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    @Mock
    private User goodUser;

    @Mock
    private User badUser;

    @Mock
    private UserRegistrationService userRegistrationService;

    @Before
    public void setup() throws UserAlreadyExistsException {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(userRegistrationService).registerNewUserAccount(goodUser);
        Mockito.doThrow(new UserAlreadyExistsException()).when(userRegistrationService).registerNewUserAccount(badUser);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    public void testLoginRoute() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"));
    }

    @Test
    public void testLoginError() throws Exception {
        mockMvc.perform(get("/login?error"))
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    public void testLoginLogoutMessage() throws Exception {
        mockMvc.perform(get("/login?logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
                .andExpect(model().attributeExists("msg"));
    }

    @Test
    public void testLogoutRoute() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }

    @Test
    public void testRegistrationRoute() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register.html"));
    }

    @Test
    public void testGoodRegistration() throws Exception {
        // Leaving blank for now. Requires testing-side JSON serialization.
    }

    @Test
    public void testBadRegistration() throws Exception {
        // Leaving blank for now. Requires testing-side JSON serialization.
    }
}
