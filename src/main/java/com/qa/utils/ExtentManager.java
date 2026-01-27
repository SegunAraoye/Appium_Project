package com.qa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    static {
        ExtentSparkReporter reporter =
            new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    public static void startTest() {
        test.set(extent.createTest("Mobile Automation Test"));
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void endTest() {
        extent.flush();
    }
}
