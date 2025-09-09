package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class HRAppHomePage {

    public HRAppHomePage(){
        WebDriver driver= Driver.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "deptName")
    public WebElement departmentNameTextBox;

    @FindBy(id = "deptLocation")
    public WebElement departmentLocationIDTextBox;

    @FindBy(xpath = "//button[text()='Add Department']")
    public WebElement addDepartmentBtn;

}
