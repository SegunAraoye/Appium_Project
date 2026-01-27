package com.qa.base;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.*;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class BasePage {

    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, 15);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);
    }

    protected void scrollToText(String text) {
        driver.findElement(MobileBy.AndroidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true))" +
            ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"
        ));
    }

    protected void scrollToResourceId(String id) {
        driver.findElement(MobileBy.AndroidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true))" +
            ".scrollIntoView(new UiSelector().resourceId(\"" + id + "\"))"
        ));
    }

	public boolean downloadCertificate() {
		return true;
	}

	public boolean isFileDownloaded() {
		return false;
	}
	private void typeByText(String text, String value) {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + text + "\")")))
				.sendKeys(value);
    }

	protected void scrollIntoViewByText(String text) {driver.findElement(MobileBy.AndroidUIAutomator
			("new UiScrollable(new UiSelector().scrollable(true))"
			+ ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"));
}
	
	 public void scrollBackward() {
	        driver.findElement(MobileBy.AndroidUIAutomator(
	            "new UiScrollable(new UiSelector().scrollable(true)).scrollBackward()"
	        ));
	    }
	 
	 public void scrollForward() {
	        driver.findElement(MobileBy.AndroidUIAutomator(
	            "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
	        ));
	 }
	

	protected void clickByText(String text) {
		wait.until(ExpectedConditions
				.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + text + "\")")))
				.click();
		
	}
}
