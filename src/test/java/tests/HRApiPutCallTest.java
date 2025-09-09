package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.EmployeeRequest;
import utilities.HrApiUtils;
import utilities.TestBase;

public class HRApiPutCallTest extends TestBase {

    @Test(groups = {"regression", "smoke"})
    public void updateEmployeeApiTest() {
        // Create employee
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setDefaultValues();
        Response postResponse = HrApiUtils.postCall("/employees", employeeRequest);
        Assert.assertEquals(postResponse.getStatusCode(), 201);
        int employeeId = postResponse.body().jsonPath().getInt("employee_id");

        // Update employee data
        String expectedNewName = "David";
        employeeRequest.setFirst_name(expectedNewName);
        Response putResponse = HrApiUtils.putCall("/employees/" + employeeId, employeeRequest);
        Assert.assertEquals(putResponse.getStatusCode(), 200);
        String actualNewFirstName = putResponse.body().jsonPath().getString("first_name");
        Assert.assertEquals(actualNewFirstName, expectedNewName);

        // Get Call and validate new name in response
        Response getResponse = HrApiUtils.getCall("/employees/" + employeeId);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        String getCallNewName = getResponse.body().jsonPath().getString("first_name");
        Assert.assertEquals(getCallNewName, expectedNewName);
    }

    @Test(groups = {"regression"})
    public void updateEmployeeApiInvalidNameTest(){
        // Create employee
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setDefaultValues();
        Response postResponse = HrApiUtils.postCall("/employees", employeeRequest);
        Assert.assertEquals(postResponse.getStatusCode(), 201);
        int employeeId = postResponse.body().jsonPath().getInt("employee_id");

        // Update employee data
        String expectedNewName = "";
        employeeRequest.setFirst_name(expectedNewName);
        Response putResponse = HrApiUtils.putCall("/employees/" + employeeId, employeeRequest);
        Assert.assertEquals(putResponse.getStatusCode(), 400);
        String errorMessage = putResponse.body().jsonPath().getString("error");
        Assert.assertEquals(errorMessage, "All employee fields are required");
    }

}
