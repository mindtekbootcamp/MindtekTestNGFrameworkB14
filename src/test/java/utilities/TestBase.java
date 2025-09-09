package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    protected WebDriver driver;

//    @BeforeMethod(groups = {"testbase"})
//    public void setup(){
//        driver = Driver.getDriver();
//    }
//
//    @AfterMethod(groups = {"testbase"})
//    public void teardown(){
//        driver.quit();
//    }

    @BeforeClass(groups = {"regression", "smoke"})
    public void beforeClass(){
        HrApiUtils.existingDeptId=HrApiUtils.getExistingDeptId();
    }
}
