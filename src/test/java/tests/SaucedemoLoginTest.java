package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SaucedemoLoginPage;
import pages.SaucedemoMainPage;
import utilities.TestBase;

public class SaucedemoLoginTest extends TestBase {

    @Test(groups = {"regression", "smoke", "saucedemo", "login", "positive"})
    public void loginPositive(){                                    // positive outcome expected
        SaucedemoLoginPage saucedemoLoginPage = new SaucedemoLoginPage();
        saucedemoLoginPage.saucedemoLogin("sdStandardUser", "sdPassword");
        SaucedemoMainPage saucedemoMainPage = new SaucedemoMainPage();
        Assert.assertTrue(saucedemoMainPage.hamburgerMenu.isDisplayed(), "Hamburger menu is not displayed, or the main page hasn't loaded");
    }

    @Test(groups = {"regression", "smoke", "saucedemo", "login", "negative"}, priority = 1)
    public void loginNegative(){                                     // negative outcome expected
        SaucedemoLoginPage saucedemoLoginPage = new SaucedemoLoginPage();
        saucedemoLoginPage.saucedemoLogin("sdLockedOutUser", "sdPassword");
        Assert.assertEquals(saucedemoLoginPage.errorMessage.getText(), "Epic sadface: Sorry, this user has been locked out.");
    }
}
