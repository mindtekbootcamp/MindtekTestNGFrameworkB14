package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class MagentoSignInPage {

    WebDriver driver;

    public MagentoSignInPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "email")
    public WebElement emailInput;

    @FindBy(id = "pass")
    public WebElement passwordInput;

    @FindBy(id = "send2")
    public WebElement signInBtn;
}
