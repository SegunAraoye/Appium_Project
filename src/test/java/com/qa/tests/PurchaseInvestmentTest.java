package com.qa.tests;

import com.google.gson.JsonObject;
import com.qa.base.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.CertificatePage;
import com.qa.utils.JsonUtils;
import com.qa.utils.ValidationUtils;
import org.testng.annotations.Listeners;
import com.qa.utils.TestListener;
import org.testng.annotations.Test;


@Listeners(TestListener.class)
public class PurchaseInvestmentTest extends BaseTest {

	@Test(
		    description = "Verify investment purchase and certificate flow",
		    groups = {"Investment", "Certificate"}
		)
    public void purchaseInvestmentFlow() {

        ValidationUtils validation = new ValidationUtils(driver);

        // -------- TEST DATA --------
        JsonObject data = JsonUtils.getTestData("loginUsers.json", "validUser");
        validation.assertNotNull(data, "Test data loaded successfully");

        String email = data.get("email").getAsString();
        String password = data.get("password").getAsString();
        int amount = data.get("amount").getAsInt();
        int tenor = data.get("Tenor").getAsInt();
        int fixed = data.get("Fixed").getAsInt();

        // -------- LOGIN & INVESTMENT FLOW --------
        LoginPage loginPage = new LoginPage(driver);
        boolean loginSuccess =
                loginPage.launchAndLogin(email, password, amount, tenor);

        validation.assertTrue(
            loginSuccess,
            "Login and investment flow completed successfully"
        );

        // -------- OVERVIEW SCREEN VALIDATION --------
        boolean overviewDisplayed = loginPage.isOverviewDisplayed();

        validation.assertTrue(
            overviewDisplayed,
            "Overview screen displayed after flow"
        );

        // -------- CERTIFICATE FLOW --------
        CertificatePage certificatePage = new CertificatePage(driver);
        boolean certificateDownloaded = certificatePage.downloadCertificate();

        validation.assertTrue(
            certificateDownloaded,
            "Investment certificate downloaded successfully"
        );

        // -------- FINAL ASSERT --------
        validation.assertAll("PurchaseInvestmentTest completed");
    }
}
