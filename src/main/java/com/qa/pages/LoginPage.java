package com.qa.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.qa.base.BasePage;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class LoginPage extends BasePage {

    public LoginPage(AndroidDriver driver) {
		super(driver);}

	private By getStartedBtn = By.id("com.unionbankng.marcusproject.debug:id/btn_get_started");
    private By loginBtn = By.id("com.unionbankng.marcusproject.debug:id/btn_log_in");
    private By emailField = By.xpath("//android.widget.EditText[@text='Email or Phone number or Username']");
    private By passwordField = By.xpath("//android.widget.EditText[@text='Password']");
    private By loginSubmitBtn = By.id("com.unionbankng.marcusproject.debug:id/btn_login");
    private By agreeBtn = By.id("com.unionbankng.marcusproject.debug:id/btnAgree");
    private By amountField = By.id("com.unionbankng.marcusproject.debug:id/edt_amount");
    private By proceedBtn = By.id("com.unionbankng.marcusproject.debug:id/btn_proceed");
    private By buyBtn = By.id("com.unionbankng.marcusproject.debug:id/btn_buy");
    private By overviewText = MobileBy.AndroidUIAutomator("new UiSelector().text(\"Overview\")");

    public boolean launchAndLogin(String email, String password, int amount, int tenor) {
    	

        click(getStartedBtn, "Get Started button");
        click(getStartedBtn, "Get Started confirmation");
        click(loginBtn, "Login button");

        type(emailField, email, "Email input");
        type(passwordField, password, "Password input");
        click(loginSubmitBtn, "Submit login"); 
        click(agreeBtn, "Agree to terms");

        clickByText("Overview", "Navigate to Overview");

        // Treasury Bills navigation
        click(
            MobileBy.AndroidUIAutomator(
                "new UiSelector().className(\"android.widget.FrameLayout\").instance(6)"
            ),
            "Select Treasury Bills"
        );

        click(
            MobileBy.AndroidUIAutomator(
                "new UiSelector().className(\"android.view.ViewGroup\").instance(1)"
            ),
            "Select first product"
        );

        type(amountField, String.valueOf(amount), "Enter investment amount");
        click(proceedBtn, "Proceed button");

        scrollIntoViewByText("Proceed", "Scroll to Proceed");
        clickByText("Proceed", "Confirm Proceed");

        click(buyBtn, "Buy investment");

        click(
            By.id("com.unionbankng.marcusproject.debug:id/tv_close"),
            "Close success modal"
        );

        clickByText("Overview", "Return to Overview");

        // Second investment flow
        click(
            MobileBy.AndroidUIAutomator(
                "new UiSelector().className(\"android.widget.LinearLayout\").instance(10)"
            ),
            "Select Fixed Deposit"
        );

        click(
            MobileBy.AndroidUIAutomator(
                "new UiSelector().className(\"android.widget.LinearLayout\").instance(3)"
            ),
            "Select product option"
        );

        type(amountField, String.valueOf(amount), "Enter fixed amount");

        type(
            By.id("com.unionbankng.marcusproject.debug:id/spn_tenor"),
            String.valueOf(tenor),
            "Enter tenor"
        );

        scrollForward("Scroll forward");
        clickByText("Continue", "Continue investment");
        scrollBackward("Scroll back");

		/*
		 * click( MobileBy.AccessibilityId("Open navigation drawer"),
		 * "Open navigation drawer" );
		 */

        scrollBackward("Scroll menu");
        click(
            MobileBy.AccessibilityId("Open navigation drawer"),
            "Close navigation drawer"
        );
        click(
                MobileBy.AccessibilityId("Open navigation drawer"),
                "Close navigation drawer"
            );

        logout();
		return false;
    	}
       
    private void logout() {
        click(
            MobileBy.AndroidUIAutomator(
                "new UiSelector().resourceId(\"com.unionbankng.marcusproject.debug:id/navigation_bar_item_icon_view\").instance(4)"
            ),
            "Open profile tab"
        );
        click(
                MobileBy.AndroidUIAutomator(
                    "new UiSelector().resourceId(\"com.unionbankng.marcusproject.debug:id/navigation_bar_item_icon_view\").instance(4)"
                ),
                "Open profile tab"
            );

        click(
            MobileBy.AndroidUIAutomator(
                "new UiSelector().className(\"android.widget.RelativeLayout\").instance(7)"
            ),
            "Logout"
        );

        click(
            By.id("com.unionbankng.marcusproject.debug:id/btn_buy"),
            "Confirm logout"
        );
    }

    /* ========= VALIDATION (NO ASSERTION HERE) ========= */

    public boolean isOverviewDisplayed() {
        return isElementVisible(overviewText, "Overview screen visible");
    }
}
