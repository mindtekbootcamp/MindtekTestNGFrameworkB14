package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.CreateBookingRequest;
import pojos.CreateBookingResponse;
import utilities.ConfigReader;

import static io.restassured.RestAssured.given;

public class BookingApiPostTest {

    @Test(groups = {"regression","smoke"})
    public void createBookingApiTest(){
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

        Response getResponse=given().baseUri(ConfigReader.getProperty("BookingAPIBaseUrl"))
                .and().header("Accept","application/json")
                .and().log().all()
                .when().get("/booking/"+bookingId);
        getResponse.then().log().all();
        getResponse.then().statusCode(200);
    }

    @Test(groups = {"regression"})
    public void createBookingApiNegativeTest(){
        CreateBookingRequest createBookingRequest=new CreateBookingRequest();
        createBookingRequest.setDefaultValues();
        createBookingRequest.setFirstname("");
        Response postResponse=given().baseUri(ConfigReader.getProperty("BookingAPIBaseUrl"))
                .and().header("Accept","application/json")
                .and().header("Content-Type","application/json")
                .and().body(createBookingRequest) // POJO -> Json ====> SERIALIZATION
                .and().log().all()
                .when().post("/booking");
        postResponse.then().log().all();
        postResponse.then().statusCode(400);
    }

}
