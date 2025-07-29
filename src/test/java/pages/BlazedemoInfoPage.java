package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class BlazedemoInfoPage {

    WebDriver driver;

    public BlazedemoInfoPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//p[1]")
    public WebElement airline;

    @FindBy(xpath = "//p[2]")
    public WebElement flightNum;

    @FindBy(xpath = "//p[3]")
    public WebElement price;
}
