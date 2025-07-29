package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class MagentoMainPage {

    WebDriver driver;

    public MagentoMainPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "Create an Account")
    public WebElement createAccountLink;

    @FindBy(linkText = "Sign In")
    public WebElement signInLink;
}
