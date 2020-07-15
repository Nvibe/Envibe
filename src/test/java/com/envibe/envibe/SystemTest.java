package com.envibe.envibe;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
public class SystemTest {

    @LocalServerPort
    private int port;

    protected WebDriver driver;

    protected String DEFAULT_USERNAME = "listener";
    protected String DEFAULT_PASSWORD = "envibe";

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    /**
     * Helper function that returns the FQDN of the active server with a relative URI endpoint path with any attached GET parameters. Performs no URI validation.
     * @param sub_path Relative URI of desired endpoint.
     * @return FQDN with desired endpoint path attached.
     * @see EnvibeApplicationTests#port
     */
    protected String getURI(String sub_path) {
        return "http://localhost:" + port + sub_path;
    }
}
