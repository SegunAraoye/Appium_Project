package com.qa.utils;

import com.qa.base.DriverManager;

import io.appium.java_client.MobileBy;

public class TestUtil {

    public static void scrollDownToText(String text) {
        DriverManager.getDriver().findElement(
            MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"
            )
        );
    }
}
