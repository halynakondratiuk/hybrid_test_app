package com.epam.hybridapptest;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppTest {
    private AppiumDriver driver;
    private WebElement searchInput;
    private WebElement btnNext;

    @BeforeTest
    public void setUp() throws Exception {
        File appDir = new File("apps");
        File app = new File(appDir, "browser.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("deviceName", "Testing Phone");
        capabilities.setCapability("appPackage", "com.android.chrome");
        capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");

        capabilities.setCapability("app", app.getAbsolutePath());
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void openNewTab() {
        By newTab = By.id("com.android.chrome:id/tab_switcher_button");
        driver.findElement(newTab).click();
        By plusTab = By.id("com.android.chrome:id/new_tab_button");
        driver.findElement(plusTab).click();
    }

    @Test
    public void enterValueInSearchFieldAndAssertIt() {
        By search = By.id("q");
        searchInput = driver.findElement(search);
        searchInput.sendKeys("https://mail.google.com" + "\n");
        btnNext = driver.findElement(By.id("identifierNext"));
        String btnTitle = btnNext.getText();
        Assert.assertEquals(btnTitle, "Next");
    }


    @AfterTest
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
