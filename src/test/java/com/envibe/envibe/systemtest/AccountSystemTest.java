package com.envibe.envibe.systemtest;

import com.envibe.envibe.SystemTest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;

public class AccountSystemTest extends SystemTest {

    private static final String TEST_STRING = "testValue";

    @Test
    public void testFirstNameUpdate() {
        updateAndSubmit("FNvalue", TEST_STRING);
    }

    @Test
    public void testLastNameUpdate() {
        updateAndSubmit("LNvalue", TEST_STRING);
    }

    @Test
    public void testPasswordUpdate() {
        updateAndSubmit("Passvalue", TEST_STRING);
    }

    @Test
    public void testEmailUpdate() {
        updateAndSubmit("Emailvalue", "user44@example.com");
    }

    @Test
    public void testCountryUpdate() {
        updateAndSubmit("Countryvalue", TEST_STRING);
    }

    @Test
    public void testImageLinkUpdate() {
        updateAndSubmit("ImageLink", "http://example.com/image.png");
    }

    @Test
    public void testDOBUpdate() {
        updateAndSubmit("Birthvalue", "01/05/2017");
    }

    protected void updateAndSubmit(String fieldName, String inputValue) {
        driver.findElement(By.id(fieldName)).sendKeys(inputValue);
        driver.findElement(By.id("SaveButton")).click();
    }

    @BeforeEach
    private void navigateToAccountPage() {
        authenticate();
        driver.findElement(By.className("MyAccountText")).click();
    }
}
