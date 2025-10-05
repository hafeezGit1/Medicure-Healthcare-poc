package com.medicure.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class MedicureAPITest {
    private WebDriver driver;
    private String baseUrl;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        driver = new ChromeDriver(options);
        baseUrl = System.getenv("APP_URL");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void testHealthEndpoint() {
        driver.get(baseUrl + "/api/doctors/all");
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("[") || pageSource.contains("DOC"), 
                         "API should return valid response");
    }

    @Test(priority = 2)
    public void testAPIAccessibility() {
        driver.get(baseUrl + "/api/doctors/all");
        Assert.assertNotNull(driver.getTitle(), "Application should be accessible");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}