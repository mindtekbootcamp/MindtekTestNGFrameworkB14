package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.EmployeeRequest;
import utilities.HrApiUtils;
import utilities.TestBase;

public class HrApiDeleteCallTest extends TestBase {

    @Test(groups = {"regression"})
    public void deleteEmployeeApiTest(){
        // Create employee
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setDefaultValues();
        Response postResponse = HrApiUtils.postCall("/employees", employeeRequest);
        Assert.assertEquals(postResponse.getStatusCode(), 201);
        int employeeId = postResponse.body().jsonPath().getInt("employee_id");

        // Delete employee
        Response deleteResponse = HrApiUtils.deleteCall("/employees/"+employeeId,"admin");
        Assert.assertEquals(deleteResponse.getStatusCode(),204);

        // Get and validate 404 status code
        Response getResponse=HrApiUtils.getCall("/employees/"+employeeId);
        Assert.assertEquals(getResponse.getStatusCode(),404);
    }

}
