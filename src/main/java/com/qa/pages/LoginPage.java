package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.qa.base.BasePage;

import io.appium.java_client.MobileBy;

public class LoginPage extends BasePage {

	private By getStarted = By.id("com.unionbankng.marcusproject.debug:id/btn_get_started");
	private By loginBtn = By.id("com.unionbankng.marcusproject.debug:id/btn_log_in");

	public void launchAndLogin(String email, String password, int amount, int Tenor, int Fixed) {
		click(getStarted);
		click(getStarted);
		click(loginBtn);

		type(By.xpath("//android.widget.EditText[@text='Email or Phone number or Username']"), email);
		type(By.xpath("//android.widget.EditText[@text='Password']"), password);
		click(By.id("com.unionbankng.marcusproject.debug:id/btn_login"));
		click(By.id("com.unionbankng.marcusproject.debug:id/btnAgree"));

		// Navigate to Overview
		clickByText("Overview");

		// Treasury Bills
		driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.FrameLayout\").instance(6)")).click();

		driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(1)")).click();

		// Enter Amount
		WebElement amnt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.unionbankng.marcusproject.debug:id/edt_amount")));
		amnt.sendKeys("15000");

		click(By.id("com.unionbankng.marcusproject.debug:id/btn_proceed"));

		scrollIntoViewByText("Proceed");
		clickByText("Proceed");

		// Buy investment
		click(By.id("com.unionbankng.marcusproject.debug:id/btn_buy"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.unionbankng.marcusproject.debug:id/tv_close"))).click();
		
		// Navigate to Overview
		clickByText("Overview");
		
		driver.findElement(MobileBy.AndroidUIAutomator ("new UiSelector().className(\"android.widget.LinearLayout\").instance(10)")).click();
		
		driver.findElement(MobileBy.AndroidUIAutomator ("new UiSelector().className(\"android.widget.LinearLayout\").instance(3)")).click();
		
		WebElement amount1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.unionbankng.marcusproject.debug:id/edt_amount")));
		amount1.sendKeys("15000");
		
		WebElement tenor = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.unionbankng.marcusproject.debug:id/spn_tenor")));
		tenor.sendKeys("365");
		
		scrollForward();
		clickByText("Continue");
		scrollBackward();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().description(\"Open navigation drawer\")"))).click();
		scrollBackward();
		wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().description(\"Open navigation drawer\")"))).click();

		
		
		// Logout
		wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.unionbankng.marcusproject.debug:id/navigation_bar_item_icon_view\").instance(4)"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().className(\\\"android.widget.RelativeLayout\\\").instance(7)"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.unionbankng.marcusproject.debug:id/btn_buy"))).click();
		
		
		// Assertion
		WebElement errTxt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.unionbankng.marcusproject.debug:id/tv_upgrade_account")));
		Assert.assertEquals(errTxt.getText(), "Request Unsuccessful..");
	}

}
