package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

import java.time.Duration;

public class CertificatePage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    public CertificatePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
    }

    public boolean downloadCertificate() {

        // Open certificate
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("com.unionbankng.marcusproject.debug:id/btnCertificate")))
                .click();

        // Scroll to Download
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                        + ".scrollIntoView(new UiSelector().textContains(\"Download\"))"));

        // Click Download
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("com.unionbankng.marcusproject.debug:id/download_receipt")))
                .click();

        return true;
    }
}

