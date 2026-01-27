package com.qa.base;

import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.qa.utils.ExtentManager;

import io.appium.java_client.android.AndroidDriver;


public class BaseTest {

    protected AndroidDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
       //driver = DriverFactory.initDriver();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "14");
        caps.setCapability("deviceName", "RZCT31F32FP");
        caps.setCapability("udid", "RZCT31F32FP");
        caps.setCapability("appPackage", "com.unionbankng.marcusproject.debug");
        caps.setCapability("appActivity", "com.unionbankng.marcusproject.ui.MainActivity");
        caps.setCapability("autoGrantPermissions", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        DriverManager.setDriver(driver);

        ExtentManager.startTest();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        DriverManager.unload();
        ExtentManager.endTest();
    }
}
