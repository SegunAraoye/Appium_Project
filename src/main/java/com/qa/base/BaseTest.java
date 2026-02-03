package com.qa.base;

import java.lang.reflect.Method;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.qa.utils.ExtentManager;
import com.qa.utils.StepCounter;

import io.appium.java_client.android.AndroidDriver;

public class BaseTest {

    protected AndroidDriver driver;
    protected ExtentTest test;

    /* ------------------ EXTENT SETUP ------------------ */

    @BeforeSuite(alwaysRun = true)
    public void setupExtent() {
        // 🔹 Single source of truth
        ExtentManager.getExtent();
    }

    /* ------------------ DRIVER SETUP ------------------ */

    @Parameters({
        "platformVersion",
        "deviceName",
        "udid"
    })
    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method,
                      @Optional("14") String platformVersion,
                      @Optional("RZCT31F32FP") String deviceName,
                      @Optional("RZCT31F32FP") String udid)
            throws Exception {

        // 🔹 Reset step counters per test
        StepCounter.reset();

        // 🔹 Create Extent test
        test = ExtentManager.getExtent().createTest(method.getName());
        ExtentManager.setTest(test);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", platformVersion);
        caps.setCapability("deviceName", deviceName);
        caps.setCapability("udid", udid);
        caps.setCapability("appPackage", "com.unionbankng.marcusproject.debug");
        caps.setCapability("appActivity", "com.unionbankng.marcusproject.ui.MainActivity");
        caps.setCapability("autoGrantPermissions", true);

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                caps
        );

        DriverManager.setDriver(driver);

        test.info("📱 App launched on device: " + deviceName);
    }

    /* ------------------ TEARDOWN ------------------ */

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        // 🔹 Inject step summary (THIS is what stakeholders want)
        test.info("📊 Step Summary");
        test.info("✅ Steps Passed: " + StepCounter.getPassedSteps());
        test.info("❌ Steps Failed: " + StepCounter.getFailedSteps());

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("❌ Test Failed");
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("✅ Test Passed");
        } else {
            test.skip("⚠️ Test Skipped");
        }

        if (driver != null) {
            driver.quit();
        }

        DriverManager.unload();
        ExtentManager.unload();
    }

    /* ------------------ REPORT FLUSH ------------------ */

    @AfterSuite(alwaysRun = true)
    public void flushExtent() {
        ExtentManager.flush();
    }
}
