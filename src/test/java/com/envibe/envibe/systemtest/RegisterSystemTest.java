package com.envibe.envibe.systemtest;

import com.envibe.envibe.SystemTest;
import org.junit.Test;
import org.openqa.selenium.By;

public class RegisterSystemTest extends SystemTest {
    @Test
    public void testCreateAccount() {
        driver.get(getURI("/register"));
        driver.findElement(By.id("Username")).sendKeys("testuser1");
        driver.findElement(By.id("Password")).sendKeys("testuser1");
        driver.findElement(By.id("Email")).sendKeys("testuser1@example.com");
        driver.findElement(By.id("Country")).sendKeys("United States");
        driver.findElement(By.id("Birthday")).sendKeys("01/24/1940");
        driver.findElement(By.id("LastName")).sendKeys("testLastName");
        driver.findElement(By.id("FirstName")).sendKeys("testFirstName");
        driver.findElement(By.id("CreateAccount")).click();
    }
}
