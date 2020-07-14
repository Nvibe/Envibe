package com.envibe.envibe.unittest;

import com.envibe.envibe.controller.HomeController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HomeControllerUnitTest {
    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        // Process mock annotations.
        MockitoAnnotations.initMocks(this);

        // Setup Spring test in standalone mode.
        this.mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void testIndexRoute() throws Exception {
        // Can't really test this because this endpoint just redirects to
        // /login on the new codebase.
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
