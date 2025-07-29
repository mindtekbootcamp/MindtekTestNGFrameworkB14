package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class SaucedemoMainPage {

    WebDriver driver;

    public SaucedemoMainPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "react-burger-menu-btn")
    public WebElement hamburgerMenu;

    @FindBy(css = "select[class='product_sort_container']")
    public WebElement sortingDropdown;

    @FindBy(xpath = "//div[@class='inventory_item_price']")
    public List<WebElement> itemPrices;
}
