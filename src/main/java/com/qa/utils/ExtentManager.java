package com.qa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private static final String REPORT_PATH =
            "test-output/ExtentReport.html";

    private ExtentManager() {
        // prevent instantiation
    }

    /* ================= REPORT INITIALIZATION ================= */

    public static synchronized ExtentReports getExtent() {
        if (extent == null) {
            ExtentSparkReporter reporter =
                    new ExtentSparkReporter(REPORT_PATH);

            reporter.config().setReportName("UnionBank Mobile Automation Report");
            reporter.config().setDocumentTitle("Execution Summary");

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            // ✅ System Info (Dashboard)
            extent.setSystemInfo("Platform", "Android");
            extent.setSystemInfo("Automation", "Appium");
            extent.setSystemInfo("Framework", "POM + TestNG");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    /* ================= TEST MANAGEMENT ================= */

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        if (test.get() == null) {
            throw new IllegalStateException(
                "ExtentTest is not initialized. Call setTest() before logging."
            );
        }
        return test.get();
    }

    public static void unload() {
        test.remove();
    }

    /* ================= FLUSH ================= */

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
