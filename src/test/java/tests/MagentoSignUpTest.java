package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.MagentoCreateAccountPage;
import pages.MagentoMainPage;
import pages.MagentoMyAccountPage;
import pages.MagentoSignInPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.TestBase;

@SuppressWarnings("StringTemplateMigration")
public class MagentoSignUpTest extends TestBase {

    String firstName = "";
    String lastName = "";
    String email = "";
    String password = "";

    @DataProvider(name = "createAccountData")
    public static Object[][] testData(){
        return new Object[][]{
                {"Harsh", "Patel", BrowserUtils.uuidEmailGenerator(), "vV12345#"},
                {"Mary-Beth", "Lee", BrowserUtils.uuidEmailGenerator(), "vV12345"},
                {"Jane", "Doe", BrowserUtils.uuidEmailGenerator(), "vV12345#"}
        };
    }

    @Test(groups = {"regression", "magento", "login"}, dataProvider = "createAccountData")
    public void verifySignUp(
            String firstName,
            String lastName,
            String email,
            String password
    ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

        driver.get(ConfigReader.getProperty("magentoURL"));
        MagentoMainPage magentoMainPage = new MagentoMainPage();
        magentoMainPage.createAccountLink.click();
        MagentoCreateAccountPage magentoCreateAccountPage = new MagentoCreateAccountPage();

        BrowserUtils.removeGoogleAds();

        driver.navigate().refresh();
        magentoMainPage.createAccountLink.click();

        magentoCreateAccountPage.firstNameInput.sendKeys(firstName);
        magentoCreateAccountPage.lastNameInput.sendKeys(lastName);

        System.out.println(email);

        magentoCreateAccountPage.emailInput.sendKeys(email);
        magentoCreateAccountPage.passwordInput.sendKeys(password);
        magentoCreateAccountPage.confirmPasswordInput.sendKeys(password);
        magentoCreateAccountPage.createAccountBtn.click();

        MagentoMyAccountPage magentoMyAccountPage = new MagentoMyAccountPage();
        if(!firstName.equals("Mary-Beth")){
            Assert.assertEquals(
                    magentoMyAccountPage.welcomeMessage.getText(),
                    "Welcome, "+firstName+" "+lastName+"!"
            );
            Assert.assertEquals(driver.getTitle(), "My Account");
        } else {
            Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
        }
    }

    @Test(groups = {"regression", "magento", "login"}, dependsOnMethods = "verifySignUp")
    public void verifySignIn(){
        System.out.println(email);
        System.out.println(password);

        driver.get(ConfigReader.getProperty("magentoURL"));
        MagentoMainPage magentoMainPage = new MagentoMainPage();
        magentoMainPage.signInLink.click();
        MagentoSignInPage magentoSignInPage = new MagentoSignInPage();

        driver.navigate().refresh();
        magentoMainPage.signInLink.click();

        magentoSignInPage.emailInput.sendKeys(email);
        magentoSignInPage.passwordInput.sendKeys(password);
        magentoSignInPage.signInBtn.click();

        MagentoMyAccountPage magentoMyAccountPage = new MagentoMyAccountPage();
        Assert.assertEquals(
                magentoMyAccountPage.welcomeMessage.getText(),
                "Welcome, "+firstName+" "+lastName+"!"
        );
    }
}
