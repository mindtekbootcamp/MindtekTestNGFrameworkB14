package utilities;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class HrApiUtils {

    public static int existingDeptId;

    /*
    getCall(String endpoint, Map<String, Object> queryParams); -> returns response
    getCall(String endpoint); -> returns response

    postCall(String endpoint, Object body); -> returns response

    putCall(String endpoint, Object body); -> returns response
    deleteCall(String endpoint); -> returns response
     */
    public static Response getCall(String endpoint, Map<String, Object> queryParams){
        Response getResponse=given().baseUri(ConfigReader.getProperty("HRAPIBaseUrl"))
                .and().accept("application/json")
                .and().header("Authorization","Bearer "+generateTokenFor("hruser"))
                .and().queryParams(queryParams)
                .and().log().all()
                .when().get(endpoint);
        getResponse.then().log().all();
        return getResponse;
    }

    public static Response getCall(String endpoint){
        Response getResponse=given().baseUri(ConfigReader.getProperty("HRAPIBaseUrl"))
                .and().accept("application/json")
                .and().header("Authorization","Bearer "+generateTokenFor("hruser"))
                .and().log().all()
                .when().get(endpoint);
        getResponse.then().log().all();
        return getResponse;
    }

    public static Response postCall(String endpoint, Object body){
        Response postResponse=given().baseUri(ConfigReader.getProperty("HRAPIBaseUrl"))
                .and().accept("application/json")
                .and().contentType("application/json")
                .and().header("Authorization","Bearer "+generateTokenFor("hruser"))
                .and().body(body)
                .and().log().all()
                .when().post(endpoint);
        postResponse.then().log().all();
        return postResponse;
    }

    public static Response putCall(String endpoint, Object body){
        Response putResponse=given().baseUri(ConfigReader.getProperty("HRAPIBaseUrl"))
                .and().accept("application/json")
                .and().contentType("application/json")
                .and().header("Authorization","Bearer "+generateTokenFor("hruser"))
                .and().body(body)
                .and().log().all()
                .when().put(endpoint);
        putResponse.then().log().all();
        return putResponse;
    }

    public static Response deleteCall(String endpoint, String role){
        Response deleteResponse=given().baseUri(ConfigReader.getProperty("HRAPIBaseUrl"))
                .and().log().all()
                .and().header("Authorization","Bearer "+generateTokenFor(role))
                .when().delete(endpoint);
        deleteResponse.then().log().all();
        return deleteResponse;
    }

    public static int getExistingDeptId(){
        return getCall("/departments").body().jsonPath().getInt("department_id[0]");
    }

    public static String generateTokenFor(String role){
        Response postResponse = given().baseUri(ConfigReader.getProperty("HRAPIBaseUrl"))
                .and().accept("application/json")
                .and().contentType("application/json")
                .and().body("{\n" +
                        "    \"username\" : \""+role+"\",\n" +
                        "    \"password\" : \"password\"\n" +
                        "}")
                .and().log().all()
                .when().post("/login");
        return postResponse.body().jsonPath().getString("token");
    }

}
