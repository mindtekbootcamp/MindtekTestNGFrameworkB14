package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ConfigReader;
import utilities.HrApiUtils;
import java.util.List;

import static io.restassured.RestAssured.given;

public class HRApiAuthorizationTest {

    @Test(groups = {"regression","smoke"})
    public void getEmployeeApiAuthorizationTest(){
        /*
        1. Get Employee API call with valid token
        2. Validate 200 status code
         */
        Response getResponse= HrApiUtils.getCall("/employees");
        getResponse.then().statusCode(200);
    }

    @Test(groups = {"regression","smoke"})
    public void getEmployeeApiAuthorizationNegativeTest() throws InterruptedException {
        /*
        1. Get Employee API call with no token
        2. Validate 401 status code
         */
        SoftAssert softAssert=new SoftAssert();
        // No token
        Response getResponse=given().baseUri(ConfigReader.getProperty("HRAPIBaseUrl"))
                .and().accept("application/json")
                .and().log().all()
                .when().get("/employees");
        getResponse.then().log().all();
        softAssert.assertEquals(getResponse.getStatusCode(), 401);

        // Invalid token
        Response getResponse2=given().baseUri(ConfigReader.getProperty("HRAPIBaseUrl"))
                .and().accept("application/json")
                .and().header("Authorization","Bearer 89249r783hufoeygf87eu")
                .and().log().all()
                .when().get("/employees");
        getResponse2.then().log().all();
        softAssert.assertEquals(getResponse2.getStatusCode(), 401);

        // Expired token
//        String token=HrApiUtils.generateTokenFor("hruser");
//        Thread.sleep(301000);
//        Response getResponse3=given().baseUri(ConfigReader.getProperty("HRAPIBaseUrl"))
//                .and().accept("application/json")
//                .and().header("Authorization","Bearer "+token)
//                .and().log().all()
//                .when().get("/employees");
//        getResponse3.then().log().all();
//        softAssert.assertEquals(getResponse3.getStatusCode(), 401);

        softAssert.assertAll();
    }

    @Test(groups = {"regression"})
    public void deleteEmployeeApiAuthorizationTest(){
        /*
        1. Delete employee with 'hruser' token
        2. Validate 403 Forbidden 
         */
        SoftAssert softAssert=new SoftAssert();
        Response deleteResponse=HrApiUtils.deleteCall("/employees/100","hruser");
        softAssert.assertEquals(deleteResponse.getStatusCode(),403);
        /*
        1. Delete employee with 'admin' token
        2. Validate 204 status code
         */
        Response getResponse=HrApiUtils.getCall("/employees");
        List<Integer> employeeIds=getResponse.body().jsonPath().getList("employee_id");
        Integer lastEmployeeId=employeeIds.getLast();

        Response deleteResponseForAdmin=HrApiUtils.deleteCall("/employees/"+lastEmployeeId,"admin");
        softAssert.assertEquals(deleteResponseForAdmin.getStatusCode(),204);
        softAssert.assertAll();
    }

}
