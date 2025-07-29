package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class BlazedemoFlightsPage {

    WebDriver driver;

    public BlazedemoFlightsPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//tr[5]/td[1]/input")
    public WebElement chooseFlightBtn;

    @FindBy(xpath = "//tr[5]/td[2]")
    public WebElement flightNum;

    @FindBy(xpath = "//tr[5]/td[3]")
    public WebElement airline;

    @FindBy(xpath = "//tr[5]/td[6]")
    public WebElement price;
}
