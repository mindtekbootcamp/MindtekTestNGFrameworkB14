package tests;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SaucedemoLoginPage;
import pages.SaucedemoMainPage;
import utilities.BrowserUtils;
import utilities.TestBase;

import java.io.IOException;

@SuppressWarnings("StringTemplateMigration")
public class SaucedemoSortingTest extends TestBase {

    @Test(priority = 0)
    public void verifyPriceLowToHigh() throws IOException {
        SaucedemoLoginPage saucedemoLoginPage = new SaucedemoLoginPage();
        saucedemoLoginPage.saucedemoLogin("sdStandardUser", "sdPassword");
        SaucedemoMainPage saucedemoMainPage = new SaucedemoMainPage();
        BrowserUtils.selectByValue(saucedemoMainPage.sortingDropdown, "lohi");
        BrowserUtils.takeScreenshot("saucedemo");
        for (int i=0 ; i<saucedemoMainPage.itemPrices.size()-1 ; i++){
            double price1 = Double.parseDouble(saucedemoMainPage.itemPrices
                    .get(i)
                    .getText()
                    .substring(1)
            );
            double price2 = Double.parseDouble(saucedemoMainPage.itemPrices
                    .get(i+1)
                    .getText()
                    .substring(1)
            );
            System.out.println(price1+" is less than or equal to "+price2);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(price1<=price2);
            softAssert.assertAll();
        }
    }
}
