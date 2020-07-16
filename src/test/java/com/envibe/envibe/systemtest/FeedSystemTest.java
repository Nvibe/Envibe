package com.envibe.envibe.systemtest;

import com.envibe.envibe.SystemTest;
import org.junit.Test;
import org.openqa.selenium.By;

public class FeedSystemTest extends SystemTest {
    @Test
    public void testCreatePost() {
        authenticate();
        driver.findElement(By.id("CommentField")).sendKeys("testpost1");
        driver.findElement(By.id("CreateButton")).click();
    }
}
