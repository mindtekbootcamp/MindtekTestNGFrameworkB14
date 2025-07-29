package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings("StringTemplateMigration")
public class BrowserUtils {

    /**
     * Creates a Select object and selects an option
     * based on the provided "value" parameter.
     *
     * @param dropdown
     * @param value
     */
    public static void selectByValue(WebElement dropdown, String value) {
        Select select = new Select(dropdown);
        select.selectByValue(value);
    }

    /**
     * Waits for provided webelement to be clickable
     *
     * @param element
     */
    public static void waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Scrolls the page until the provided element is in view
     *
     * @param element
     */
    public static void scrollIntoView(WebElement element) {
        JavascriptExecutor jse = ((JavascriptExecutor) Driver.getDriver());
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Takes a screenshot of the browser at the moment of execution.
     *
     * @param fileName
     * @throws IOException
     */
    public static void takeScreenshot(String fileName) throws IOException {
        String path = "src/test/resources/screenshots/" + fileName + ".png";
        File screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        File file = new File(path);
        FileUtils.copyFile(screenshot, file);
    }

    /**
     * Removes all Google Ads from a page.
     */
    public static void removeGoogleAds(){
        JavascriptExecutor jse = ((JavascriptExecutor) Driver.getDriver());
        jse.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
    }

    /**
     * Generates a random email using the provided firstName and lastName parameters.
     * @param firstName
     * @param lastName
     * @return
     */
    public static String randomEmailGenerator(String firstName, String lastName){
        Random random = new Random();
        int num = random.nextInt(999999);
        return firstName+"."+lastName+num+"@gmail.com";
    }

    /**
     * Generates a UUID random email.
     * @return
     */
    public static String uuidEmailGenerator(){
        UUID uuid = UUID.randomUUID();
        return "user-"+uuid+"@gmail.com";
    }
}
