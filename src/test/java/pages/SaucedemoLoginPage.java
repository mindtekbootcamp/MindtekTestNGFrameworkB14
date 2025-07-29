package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import utilities.ConfigReader;
import utilities.Driver;

public class SaucedemoLoginPage {

    WebDriver driver;

    public SaucedemoLoginPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    public WebElement usernameInput;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(xpath = "//div[@class='error-message-container error']/h3")
    public WebElement errorMessage;

    @FindBy(name = "login-button")
    public WebElement loginBtn;

    public void saucedemoLogin(String usernameKey, String passwordKey){
        driver.get(ConfigReader.getProperty("saucedemoURL"));
        usernameInput.sendKeys(ConfigReader.getProperty(usernameKey));
        passwordInput.sendKeys(ConfigReader.getProperty(passwordKey));
        loginBtn.click();
    }
}
