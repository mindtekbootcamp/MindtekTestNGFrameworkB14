package tests;

import dataproviders.HrApiDataProviders;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.EmployeeRequest;
import utilities.ConfigReader;
import utilities.HrApiUtils;
import utilities.JDBCUtils;
import utilities.TestBase;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HRApiTest extends TestBase {

    @Test(dataProvider = "createEmployeeData", dataProviderClass = HrApiDataProviders.class, groups = {"regression", "smoke"})
    public void createEmployeeApiPositiveTest(String firstName, double salary) throws SQLException, InterruptedException {
        // 1. Send POST request with valid data
        Thread.sleep(1000);

        EmployeeRequest employeeRequest=new EmployeeRequest();
        employeeRequest.setDefaultValues();
        employeeRequest.setFirst_name(firstName);
        employeeRequest.setSalary(salary);

        Response postResponse=HrApiUtils.postCall("/employees",employeeRequest); // SERIALIZATION | Java Object -> JSON String
        String employeeId=postResponse.body().jsonPath().getString("employee_id");

        // 2. Status 201
        Assert.assertEquals(postResponse.getStatusCode(), 201);
        postResponse.then().statusCode(201);

        // 3. Validate Employee stored in database
        JDBCUtils.connectToDB(ConfigReader.getProperty("HRDBURL"),ConfigReader.getProperty("HRDBUsername"),ConfigReader.getProperty("HRDBPassword"));
        List<Map<String, Object>> dbData=JDBCUtils.executeQuery("select * from employees where employee_id="+employeeId);

        Assert.assertEquals(dbData.get(0).get("first_name"),firstName);
        Assert.assertEquals(Double.parseDouble(dbData.get(0).get("salary").toString()),salary);
        // Object -> String -> double

        // 4. Get API request for created employee
        Response getResponse=HrApiUtils.getCall("/employees/"+employeeId);

        // 5. Validate created employee data in GET response
        Assert.assertEquals(getResponse.body().jsonPath().getString("first_name"),firstName);
        Assert.assertEquals(Double.parseDouble(getResponse.body().jsonPath().getString("salary")),salary);
    }

    @Test(dataProvider = "createEmployeeInvalidData", dataProviderClass = HrApiDataProviders.class, groups = {"regression"})
    public void createEmployeeApiNegativeTest(String firstName, double salary, String expectedErrorMessage){
        /*
        1. Send POST Request with invalid first_name
        2. Status code 400 Bad Request
        3. Validate Error message in response “All employee fields are required”
         */
        // 1. Send POST request with invalid data (empty firstName)
        EmployeeRequest employeeRequest=new EmployeeRequest();
        employeeRequest.setDefaultValues();
        employeeRequest.setFirst_name(firstName);
        employeeRequest.setSalary(salary);

        Response postResponse=HrApiUtils.postCall("/employees",employeeRequest);
        String errorMessage=postResponse.body().jsonPath().getString("error");

        // 2. Status code 400 Bad Request
        postResponse.then().statusCode(400);

        // 3. Validate Error message in response “All employee fields are required”
        Assert.assertEquals(errorMessage,expectedErrorMessage);
    }

    @Test(groups = {"regression","smoke"})
    public void getEmployeesApiQueryParamsTest(){
        // 1. Get employees with limit 5
        Map<String, Object> queryParams=new HashMap<>();
        queryParams.put("limit","5");
        Response getResponse= HrApiUtils.getCall("/employees",queryParams);

        // 2. Validate response data has 5 employees
        List<Integer> employeeIds = getResponse.body().jsonPath().getList("employee_id");
        Assert.assertEquals(employeeIds.size(),5);

        getResponse.then().body("employee_id", Matchers.hasSize(5));
    }

    @Test(groups = {"regression"})
    public void getEmployeesApiQueryParamsNegativeTest(){
        // 1. Get employee with invalid limit -1
        Map<String, Object> queryParams=new HashMap<>();
        queryParams.put("limit","-1");
        Response getResponse= HrApiUtils.getCall("/employees",queryParams);

        // 2. Validate status code 400
        getResponse.then().statusCode(500);

        // 3. Validate error message "Internal Server Error"
        getResponse.then().body("error", Matchers.equalTo("Internal Server Error"));
    }

}

