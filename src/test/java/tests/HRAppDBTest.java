package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HRAppHomePage;
import utilities.ConfigReader;
import utilities.JDBCUtils;
import utilities.TestBase;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HRAppDBTest extends TestBase {

    @DataProvider(name = "departmentData")
    public Object[][] departmentdata(){
        return new Object[][]{
                {"IT","1700"},
                {"Plumbing","1800"}
        };
    }

    @Test(dataProvider = "departmentData")
    public void validateDepartmentStoredInDBTest(String departmentName, String locationId) throws SQLException, InterruptedException {
        // 1. Get last department_id from DB
        JDBCUtils.connectToDB(ConfigReader.getProperty("HRDBURL"), ConfigReader.getProperty("HRDBUsername"), ConfigReader.getProperty("HRDBPassword"));
        List<Map<String, Object>> lastDepartment=JDBCUtils.executeQuery("select max(department_id) as department_id from departments");
        int lastDepartmentId= Integer.parseInt(lastDepartment.get(0).get("department_id").toString()) ;

        // 2. Create department in UI
        driver.get(ConfigReader.getProperty("HRAppURL"));
        HRAppHomePage hrAppHomePage=new HRAppHomePage();
        hrAppHomePage.departmentNameTextBox.sendKeys(departmentName);
        hrAppHomePage.departmentLocationIDTextBox.sendKeys(locationId);
        hrAppHomePage.addDepartmentBtn.click();
        Thread.sleep(2000);

        // 3. Connect to DB and validate created department stored in DB
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select * from departments where department_id=" + (lastDepartmentId+1) );
        Assert.assertEquals(dbData.size(), 1);
        Assert.assertEquals(dbData.get(0).get("department_name").toString(), departmentName);
        Assert.assertEquals(dbData.get(0).get("location_id").toString(),locationId);
    }

    @Test
    public void validateDeletedDepartmentInDBTest() throws SQLException, InterruptedException {
        // 1. Get departmentId from database
        JDBCUtils.connectToDB(ConfigReader.getProperty("HRDBURL"), ConfigReader.getProperty("HRDBUsername"), ConfigReader.getProperty("HRDBPassword"));
        List<Map<String, Object>> firstDepartment=JDBCUtils.executeQuery("select max(department_id) as department_id from departments");
        int firstDepartmentId= Integer.parseInt(firstDepartment.get(0).get("department_id").toString());

        // 2. Delete department in UI
        driver.get(ConfigReader.getProperty("HRAppURL"));
        String xpath="//button[@onclick='deleteDepartment("+firstDepartmentId+")']";
        System.out.println(xpath);
        driver.findElement(By.xpath(xpath)).click();
        driver.switchTo().alert().accept();
        Thread.sleep(2000);

        // 3. Validate department deleted in DB
        List<Map<String,Object>> deletedDepartment=JDBCUtils.executeQuery("select * from departments where department_id = "+firstDepartmentId);
        Assert.assertTrue(deletedDepartment.isEmpty());
    }



}
