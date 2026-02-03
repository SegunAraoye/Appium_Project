package com.qa.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestListener implements ITestListener {

    private long suiteStartTime;

    @Override
    public void onStart(ITestContext context) {
        suiteStartTime = System.currentTimeMillis();
    }

    @Override
    public void onTestStart(ITestResult result) {

        // 🔹 Ensure clean counters (BaseTest also does this – safe redundancy)
        StepCounter.reset();

        // 🔹 DO NOT recreate test – reuse from BaseTest
        ExtentTest test = ExtentManager.getTest();

        if (test != null) {

            // Feature / flow grouping
            for (String group : result.getMethod().getGroups()) {
                test.assignCategory(group);
            }

            test.log(Status.INFO, "🚀 Test execution started");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentManager.getTest()
            .log(Status.PASS, "✅ Test completed successfully");

        attachStepSummaryToTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentManager.getTest().log(
            Status.FAIL,
            "❌ Critical validation failed"
        );

        ExtentManager.getTest().fail(result.getThrowable());

        attachStepSummaryToTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentManager.getTest().log(
            Status.SKIP,
            "⏭ Test skipped"
        );

        if (result.getThrowable() != null) {
            ExtentManager.getTest().skip(result.getThrowable());
        }

        attachStepSummaryToTest();
    }

    /**
     * 🔥 Stakeholder-friendly step clarity
     */
    private void attachStepSummaryToTest() {

        ExtentTest test = ExtentManager.getTest();

        if (test == null) return;

        test.info("📊 STEP EXECUTION SUMMARY");
        test.info("Total Steps: " + StepCounter.getTotalSteps());
        test.info("Passed Steps: " + StepCounter.getPassedSteps());
        test.info("Warnings: " + StepCounter.getWarningSteps());
        test.info("Failed Steps: " + StepCounter.getFailedSteps());
    }

    @Override
    public void onFinish(ITestContext context) {

        long durationSeconds =
            (System.currentTimeMillis() - suiteStartTime) / 1000;

        // ===== EXECUTIVE DASHBOARD =====
        ExtentManager.getExtent().setSystemInfo(
            "Total Tests",
            String.valueOf(context.getAllTestMethods().length)
        );

        ExtentManager.getExtent().setSystemInfo(
            "Passed Tests",
            String.valueOf(context.getPassedTests().size())
        );

        ExtentManager.getExtent().setSystemInfo(
            "Failed Tests",
            String.valueOf(context.getFailedTests().size())
        );

        ExtentManager.getExtent().setSystemInfo(
            "Skipped Tests",
            String.valueOf(context.getSkippedTests().size())
        );

        ExtentManager.getExtent().setSystemInfo(
            "Execution Time",
            durationSeconds + " seconds"
        );

        ExtentManager.getExtent().setSystemInfo(
            "Overall Status",
            context.getFailedTests().size() == 0
                ? "All critical business flows are stable ✅"
                : "Some business flows require attention ⚠️"
        );

        // ❌ NO FLUSH HERE (BaseTest owns lifecycle)
    }
}
