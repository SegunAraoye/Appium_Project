package com.qa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.qa.base.BaseTest;
import com.qa.pages.CertificatePage;
import com.qa.pages.LoginPage;
import com.qa.utils.JsonUtils;

public class PurchaseInvestmentTest extends BaseTest {

    @Test
    public void purchaseInvestmentFlow() {

        // Read test data
        JsonObject data = JsonUtils.getTestData("loginUsers.json", "validUser");

        String email = data.get("email").getAsString();
        String password = data.get("password").getAsString();
        int amount = data.get("amount").getAsInt();
        int Tenor = data.get("Tenor").getAsInt();
        int Fixed = data.get("Fixed").getAsInt();

        // LOGIN
        LoginPage login = new LoginPage();
        login.launchAndLogin(email, password, amount, Tenor, Fixed);

        // CERTIFICATE FLOW
        CertificatePage certificate = new CertificatePage(driver);
        boolean isDownloaded = certificate.downloadCertificate();

        // ASSERT
        Assert.assertTrue(isDownloaded, "Investment certificate download failed");
    }
}


