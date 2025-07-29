package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class BlazedemoMainPage {

    WebDriver driver;                                 // Step 1: Declare the driver

    public BlazedemoMainPage(){                       // Step 2: Create a Constructor of the page
        driver = Driver.getDriver();                  // Step 3: Initialize the driver
        PageFactory.initElements(driver, this); // Step 4: Use PageFactory to initialize all webelements
    }

    @FindBy(name = "fromPort")                       // driver.findElement();
    public WebElement fromPort;                      // WebElement element =

    @FindBy(name = "toPort")
    public WebElement toPort;

    @FindBy(css = "input[type='submit']")
    public WebElement findFlightsBtn;
}

















