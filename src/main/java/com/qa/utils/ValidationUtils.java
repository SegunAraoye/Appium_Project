package com.qa.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.base.DriverManager;

public class ValidationUtils {

    private final SoftAssert softAssert;
    private final WebDriver driver;

    public ValidationUtils(WebDriver driver) {
        this.driver = driver;
        this.softAssert = new SoftAssert();
    }

    /* ================= ASSERTIONS ================= */

    public void assertTrue(boolean condition, String message) {
        if (condition) {
            ExtentManager.getTest().pass(message);
            softAssert.assertTrue(condition, message); // ✅ FIXED
        } else {
            logFail(message);
        }
    }

    public void assertFalse(boolean condition, String message) {
        if (!condition) {
            ExtentManager.getTest().pass(message);
            softAssert.assertFalse(condition, message); // ✅ FIXED
        } else {
            logFail(message);
        }
    }

    public void assertNotNull(Object obj, String message) {
        if (obj != null) {
            ExtentManager.getTest().pass(message);
            softAssert.assertNotNull(obj, message);
        } else {
            logFail(message);
        }
    }

    public void fail(String message) {
        logFail(message);
    }

    /* ================= REPORTING ================= */

    private void logFail(String message) {
        try {
            String screenshotPath = captureScreenshot(message);
            ExtentManager.getTest().fail(
                message,
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );
        } catch (Exception e) {
            ExtentManager.getTest().fail(message);
        }
        softAssert.fail(message);
    }

    private String captureScreenshot(String name) {
        String safeName = name.replaceAll("[^a-zA-Z0-9_-]", "_");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = "test-output/screenshots/" + safeName + "_" + timestamp + ".png";

        File src = ((TakesScreenshot) DriverManager.getDriver())
                .getScreenshotAs(OutputType.FILE);

        File dest = new File(filePath);
        dest.getParentFile().mkdirs();
        src.renameTo(dest);

        return filePath;
    }

    /* ================= FINAL ================= */

    public void assertAll(String message) {
        ExtentManager.getTest().info(message);
        softAssert.assertAll();
    }
}
