package utilities;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestAssuredTest {

    public static void main(String[] args) {

        /*
        GET REQUEST:
            1. URL
            2. Headers
        RESPONSE:
            1. Status Code
            2. Data
         */

        Response getResponse = given().baseUri("http://localhost:3000/api")
                .and().accept("application/json")
                .and().log().all()
                .when().get("/employees");

//        System.out.println(getResponse.getStatusCode());
//        System.out.println(getResponse.getBody().print());
        getResponse.then().log().all();

        /*
        POST REQUEST:
            1. URL
            2. Headers
            3. Data (JSON)
        RESPONSE:
            1. Status Code
            2. Data
         */
        Response postResponse=given().baseUri("http://localhost:3000/api")
                .and().accept("application/json")
                .and().contentType("application/json")
                .and().body("{\n" +
                        "  \"first_name\": \"Patel\",\n" +
                        "  \"last_name\": \"Yan\",\n" +
                        "  \"email\": \"kim.yan@gmail.com\",\n" +
                        "  \"hire_date\": \"2025-08-05\",\n" +
                        "  \"job_id\": 4,\n" +
                        "  \"salary\": 12345,\n" +
                        "  \"department_id\": 24\n" +
                        "}")
                .and().log().all()
                .when().post("/employees");
        postResponse.then().log().all();
        String employeeId=postResponse.body().jsonPath().getString("employee_id");

        /*
        PUT REQUEST:
            1. URL
            2. Headers
            3. Data (JSON)
        RESPONSE:
            1. Status code
            2. Data
         */

        Response putResponse = given().baseUri("http://localhost:3000/api")
                .and().accept("application/json")
                .and().contentType("application/json")
                .and().body("{\n" +
                        "  \"first_name\": \"J\",\n" +
                        "  \"last_name\": \"Yan\",\n" +
                        "  \"email\": \"kim.yan@gmail.com\",\n" +
                        "  \"hire_date\": \"2025-08-05\",\n" +
                        "  \"job_id\": 4,\n" +
                        "  \"salary\": 12345,\n" +
                        "  \"department_id\": 24\n" +
                        "}")
                .and().log().all()
                .when().put("/employees/69");
        putResponse.then().log().all();

        /*
        DELETE REQUEST:
            1. URL
            2. Headers
        RESPONSE:
            1. Status code
         */
        Response deleteResponse=given().baseUri("http://localhost:3000/api")
                .and().log().all()
                .when().delete("/employees/"+employeeId);
        deleteResponse.then().log().all();

    }

}
