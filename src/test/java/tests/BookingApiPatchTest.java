package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.CreateBookingRequest;
import pojos.CreateBookingResponse;
import pojos.PatchBookingRequest;
import utilities.ConfigReader;

import static io.restassured.RestAssured.given;

public class BookingApiPatchTest {

    @Test(groups = {"regression"})
    public void partialUpdateBookingApiTest(){
        CreateBookingRequest createBookingRequest=new CreateBookingRequest();
        createBookingRequest.setDefaultValues();
        Response postResponse=given().baseUri(ConfigReader.getProperty("BookingAPIBaseUrl"))
                .and().header("Accept","application/json")
                .and().header("Content-Type","application/json")
                .and().body(createBookingRequest) // POJO -> Json ====> SERIALIZATION
                .and().log().all()
                .when().post("/booking");
        postResponse.then().log().all();
        postResponse.then().statusCode(200);
        CreateBookingResponse createBookingResponse=postResponse.body().as(CreateBookingResponse.class); // Json -> POJO =====> DESERIALIZATION
        Integer bookingId=createBookingResponse.getBookingid();

        PatchBookingRequest patchBookingRequest=new PatchBookingRequest();
        patchBookingRequest.setDefaultValues();
        patchBookingRequest.setFirstname("David");
        Response patchResponse=given().baseUri(ConfigReader.getProperty("BookingAPIBaseUrl"))
                .and().header("Accept","application/json")
                .and().header("Content-Type","application/json")
                .and().header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .and().body(patchBookingRequest) // POJO -> Json ====> SERIALIZATION
                .and().log().all()
                .when().patch("/booking/"+bookingId);
        patchResponse.then().log().all();
        patchResponse.then().statusCode(200);
    }

}
