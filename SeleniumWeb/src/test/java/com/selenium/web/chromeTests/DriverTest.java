package com.selenium.web.chromeTests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.web.objectRepository.DriverTestObjectRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.Assert;

import java.time.Duration;

public class DriverTest {
    static ExtentTest test;
    static ExtentReports report;

    @BeforeClass
    public static void beforeTest() {
        report = new ExtentReports("\\results\\results.html");
        test = report.startTest("GoogleTest");
    }

    @Test
    public void driverTest() {
        //-- automatically gets the lastest chrome webdriver
        WebDriverManager.chromedriver().setup();
        //--instantiates the driver
        WebDriver driver = new ChromeDriver();
        //--sets up the webdriver to wait for an element to appear before failure of step.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        //--navigate to google
        driver.get("https://www.google.com");

        //--find the search element
        WebElement searchElement = driver.findElement(DriverTestObjectRepository.BY_SEARCH_ELEMENT);

        //--send good morning into the search field
        searchElement.sendKeys("good morning");

        //log step as success, if failure, it would have failed.
        test.log(LogStatus.PASS, "Passed entering keys");

        //--submit search
        searchElement.submit();

        //log step as success, if failure, it would have failed.
        test.log(LogStatus.PASS, "Passed submitting");

        WebElement imageElement = driver.findElement(DriverTestObjectRepository.BY_IMAGE_ELEMENT);
        Assert.assertTrue(imageElement.isDisplayed());
        test.log(LogStatus.PASS, "Good morning images are shown");
        driver.close();
        driver.quit();
    }

    @AfterClass
    public static void afterTest(){
        report.endTest(test);
        report.flush();
    }
}
