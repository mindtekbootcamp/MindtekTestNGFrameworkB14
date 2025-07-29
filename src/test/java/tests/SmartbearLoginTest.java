package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.RetryFailedTests;
import utilities.TestBase;

public class SmartbearLoginTest extends TestBase {

    @Test(groups = {"regression", "smoke", "smartbear", "login", "positive"}, retryAnalyzer = RetryFailedTests.class)
    public void verifySmartbearLogin(){
        driver.get(ConfigReader.getProperty("smartbearURL"));
        WebElement username = driver.findElement(By.id("ctl00_MainContent_username"));
        WebElement password = driver.findElement(By.id("ctl00_MainContent_password"));
        WebElement loginBtn = driver.findElement(By.id("ctl00_MainContent_login_button"));

        username.sendKeys(ConfigReader.getProperty("sbUsername"));
        password.sendKeys(ConfigReader.getProperty("sbPassword"));
        loginBtn.click();
        Assert.assertEquals(driver.getTitle(), "Web Orders");
    }
}
