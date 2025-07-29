package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.TestBase;

public class FrameworkTest extends TestBase {

    @Test(groups = {"regression", "smoke"})
    public void frameworkTest(){
        driver.get(ConfigReader.getProperty("googleURl"));
        Assert.assertEquals(driver.getTitle(), "Google");
    }
}
