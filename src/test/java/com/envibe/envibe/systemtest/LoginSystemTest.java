package com.envibe.envibe.systemtest;

import com.envibe.envibe.SystemTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginSystemTest extends SystemTest {

    @Test
    public void testLoginPage() throws Exception {
        driver.get(getURI("/login"));
    }

    @Test
    public void testLoginFlow() throws Exception {
        authenticate();
    }

    @Test
    public void testLogOutFlow() throws Exception {
        driver.get("/login");
        driver.findElement(By.name("username")).sendKeys(DEFAULT_USERNAME);
        driver.findElement(By.name("password")).sendKeys(DEFAULT_PASSWORD);
        driver.findElement(By.id("LogInButton")).click();
        driver.findElement(By.id("logOutLink")).click();
    }
}
