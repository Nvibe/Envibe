package com.envibe.envibe;

import com.envibe.envibe.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class StaticPagesTests extends EnvibeApplicationTests {
    @Test
    public void testHomePage() throws Exception {
        ResponseEntity<String> response = this.restTemplate.getForEntity(getURI("/"), String.class);
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }
}
