package com.qa.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.utils.ExtentManager;
import com.qa.utils.StepCounter;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class BasePage {

    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
    }

    /* ================= ACTION HELPERS ================= */

    protected void click(By locator, String stepName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            ExtentManager.getTest().pass("Clicked: " + stepName);
            StepCounter.stepPassed();
        } catch (Exception e) {
            StepCounter.stepFailed();
            logWarning("Click issue (non-blocking): " + stepName, e);
        }
    }

    protected void type(By locator, String value, String stepName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);
            ExtentManager.getTest().pass("Entered text: " + stepName);
            StepCounter.stepPassed();
        } catch (Exception e) {
            StepCounter.stepFailed();
            logWarning("Typing issue (non-blocking): " + stepName, e);
        }
    }

    protected void clickByText(String text, String stepName) {
        try {
            wait.until(
                ExpectedConditions.elementToBeClickable(
                    MobileBy.AndroidUIAutomator(
                        "new UiSelector().text(\"" + text + "\")"
                    )
                )
            ).click();
            ExtentManager.getTest().pass("Clicked: " + stepName);
            StepCounter.stepPassed();
        } catch (Exception e) {
            StepCounter.stepFailed();
            logWarning("Click by text issue (non-blocking): " + stepName, e);
        }
    }

    /* ================= SCROLL HELPERS ================= */

    protected void scrollIntoViewByText(String text, String stepName) {
        try {
            driver.findElement(
                MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))"
                    + ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"
                )
            );
            ExtentManager.getTest().pass("Scrolled to text: " + stepName);
            StepCounter.stepPassed();
        } catch (Exception e) {
            StepCounter.stepFailed();
            logWarning("Scroll issue (non-blocking): " + stepName, e);
        }
    }

    protected void scrollForward(String stepName) {
        try {
            driver.findElement(
                MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
                )
            );
            ExtentManager.getTest().pass("Scroll forward: " + stepName);
            StepCounter.stepPassed();
        } catch (Exception e) {
            StepCounter.stepFailed();
            logWarning("Scroll forward issue (non-blocking): " + stepName, e);
        }
    }

    protected void scrollBackward(String stepName) {
        try {
            driver.findElement(
                MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollBackward()"
                )
            );
            ExtentManager.getTest().pass("Scroll backward: " + stepName);
            StepCounter.stepPassed();
        } catch (Exception e) {
            StepCounter.stepFailed();
            logWarning("Scroll backward issue (non-blocking): " + stepName, e);
        }
    }

    /* ================= VALIDATION HELPERS ================= */

    protected boolean isElementVisible(By locator, String stepName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ExtentManager.getTest().pass("Verified visibility: " + stepName);
            StepCounter.stepPassed();
            return true;
        } catch (Exception e) {
            StepCounter.stepFailed();
            logWarning("Visibility check issue: " + stepName, e);
            return false;
        }
    }

    /* ================= WARNING HANDLING ================= */

    private void logWarning(String message, Exception e) {
        try {
            String screenshotPath = captureScreenshot();
            ExtentManager.getTest().warning(
                message + " | Reason: " + e.getMessage(),
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );
        } catch (Exception ex) {
            ExtentManager.getTest().warning(message + " (Screenshot unavailable)");
        }
    }

    private String captureScreenshot() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path = "test-output/screenshots/STEP_" + timestamp + ".png";

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(path);
        dest.getParentFile().mkdirs();
        src.renameTo(dest);

        return path;
    }
}
