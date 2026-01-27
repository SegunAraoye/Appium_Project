package com.qa.utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.base.ScreenshotUtil;
import com.qa.utils.ExtentManager;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        String screenshotPath =
                ScreenshotUtil.captureScreenshot(result.getMethod().getMethodName());

        if (screenshotPath != null) {
            ExtentManager.getTest()
                .fail(result.getThrowable())
                .addScreenCaptureFromPath(screenshotPath);
        } else {
            ExtentManager.getTest().fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().pass("Test Passed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.getTest().skip("Test Skipped");
    }
}
