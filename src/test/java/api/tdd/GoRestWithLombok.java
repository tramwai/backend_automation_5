package api.tdd;

import api.pojo_classes.go_rest.CreateGoRestUser;
import api.pojo_classes.go_rest.CreateGoRestUserWithLombok;
import api.pojo_classes.go_rest.UpdateGoRestUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.hamcrest.core.IsEqual.equalTo;

public class GoRestWithLombok {

    Response response;
    /**
     * ObjectMapper is a class coming form fasterxml to convert Java object to Json
     */
    ObjectMapper objectMapper = new ObjectMapper();
    Faker faker = new Faker();

    int expectedGoRestID;
    String expectedGoRestName;
    String expectedGoRestEmail;
    String expectedGoRestGender;
    String expectedGoRestStatus;


    @BeforeTest
    public void beforeTest(){
        System.out.println("Starting the API test");
        // by having RestAssured URI set implicitly into the rest assured
        // we just add a path to the post call
        RestAssured.baseURI = ConfigReader.getProperty("GoRestBaseURI");
    }


    @Test
    public void goRestCRUDWithLombok() throws JsonProcessingException {
        // Creating a Bean object (POJO)
        CreateGoRestUserWithLombok createUser = CreateGoRestUserWithLombok
                .builder()
                .name("Tech Global")
                .email(faker.internet().emailAddress())
                .gender("female")
                .status("active")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(createUser)
                //.when().post("https://gorest.co.in/public/v2/users")
                .when().post("/public/v2/users")
                .then().log().all()
                // validating the status code with rest assured
                .and().assertThat().statusCode(201)
                // validating the response time is less than the specified one
                .time(Matchers.lessThan(4000L))
                // validating the value from the body with hamcrest
                .body("name", equalTo("Tech Global"))
                // validating the response content type
                .contentType((ContentType.JSON))
                .extract().response();

    }
}

