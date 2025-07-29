package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BlazedemoFlightsPage;
import pages.BlazedemoInfoPage;
import pages.BlazedemoMainPage;
import utilities.ConfigReader;
import utilities.RetryFailedTests;
import utilities.TestBase;


public class BlazedemoCitiesTest extends TestBase {

    String fromCity = "Boston";
    String toCity = "Rome";

    @Test(groups = {"regression", "smoke", "blazedemo", "positive"})
    public void verifyCities(){
        driver.get(ConfigReader.getProperty("blazedemoURL"));

        BlazedemoMainPage blazedemoMainPage = new BlazedemoMainPage();
        Select fromPortSelect = new Select(blazedemoMainPage.fromPort);
        Select toPortSelect = new Select(blazedemoMainPage.toPort);

        fromPortSelect.selectByValue(fromCity);
        toPortSelect.selectByValue(toCity);
        blazedemoMainPage.findFlightsBtn.click();


        WebElement findFlightsMessage = driver.findElement(By.tagName("h3"));
        Assert.assertEquals(findFlightsMessage.getText(), "Flights from Boston to Rome:");
    }

    @Test(groups = {"regression", "blazedemo", "QA-12345"})
    public void verifyFlightInfo(){
        driver.get(ConfigReader.getProperty("blazedemoURL"));
        BlazedemoMainPage blazedemoMainPage = new BlazedemoMainPage();
        Select fromPortSelect = new Select(blazedemoMainPage.fromPort);
        Select toPortSelect = new Select(blazedemoMainPage.toPort);
        fromPortSelect.selectByValue(fromCity);
        toPortSelect.selectByValue(toCity);
        blazedemoMainPage.findFlightsBtn.click();

        BlazedemoFlightsPage blazedemoFlightsPage = new BlazedemoFlightsPage();
        String expectedAirline = blazedemoFlightsPage.airline.getText();
        String expectedFlightNum = blazedemoFlightsPage.flightNum.getText();
        String expectedPrice = blazedemoFlightsPage.price.getText();
        blazedemoFlightsPage.chooseFlightBtn.click();

        BlazedemoInfoPage blazedemoInfoPage = new BlazedemoInfoPage();
        String actualAirline = blazedemoInfoPage.airline.getText();
        String actualFlightNum = blazedemoInfoPage.flightNum.getText();
        String actualPrice = blazedemoInfoPage.price.getText();

        String actualAirlineStr = actualAirline.substring(actualAirline.indexOf(":")+2);
        String actualFlightNumStr = actualFlightNum.substring(actualFlightNum.indexOf(":")+2);
        String actualPriceStr = actualPrice.substring(actualPrice.indexOf(":")+2);

//        Assert.assertEquals(actualAirlineStr, expectedAirline);        // Hard Assert stops execution after the first AssertionError (failure)
//        Assert.assertEquals(actualFlightNumStr, expectedFlightNum);
//        Assert.assertEquals(actualPriceStr, expectedPrice);

        SoftAssert softAssert = new SoftAssert();                    // SoftAssert does not stop the execution after AssertionError
        softAssert.assertEquals(actualAirlineStr, expectedAirline);
        softAssert.assertEquals(actualFlightNumStr, expectedFlightNum);
        softAssert.assertEquals(actualPriceStr, expectedPrice);
        softAssert.assertAll();                                     // assertAll() works as just one hard assertion
    }
}












