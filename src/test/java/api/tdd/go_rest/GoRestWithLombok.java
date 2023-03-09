package api.tdd.go_rest;

import api.pojo_classes.go_rest.CreateGoRestUser;
import api.pojo_classes.go_rest.CreateGoRestUserWithLombok;
import api.pojo_classes.go_rest.UpdateGoRestUser;
import api.pojo_classes.go_rest.UpdateGoRestUserWithLombok;
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

    int goRestId;

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

        System.out.println("=============Creating the user==========");
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
                .when().post("/public/v2/users")
                .then().log().all()
                // validating the status code with rest assured
                .and().assertThat().statusCode(201)
                // validating the response time is less than the specified one
                .time(Matchers.lessThan(6000L))
                // validating the value from the body with hamcrest
                .body("name", equalTo("Tech Global"))
                // validating the response content type
                .contentType((ContentType.JSON))
                .extract().response();

        System.out.println("=============Get the specific the user==========");
            // get specific user
       goRestId = response.jsonPath().getInt("id");
        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().get("/public/v2/users/" + goRestId)
                .then().log().all()
                //validating the status code with rest assured
                .and().assertThat().statusCode(200)
                //validating the response time is less than the specified one
                .time(Matchers.lessThan(6000L))
                //validating the value from the body with hamcrest
                .body("name", equalTo("Tech Global"))
                //validating the response content type
                .contentType(ContentType.JSON)
                .extract().response();


        System.out.println("=============Updating the user==========");
        // update user
        // build the update java object body
        UpdateGoRestUserWithLombok updateGoRestUserWithLombok = UpdateGoRestUserWithLombok
                .builder()
                .email(faker.internet().emailAddress())
                .gender("male")
                .status("inactive")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(updateGoRestUserWithLombok)
                .when().put("/public/v2/users/" + goRestId)
                .then().log().all()
                // validating the status code with rest assured
                .and().assertThat().statusCode(200)
                // validating the response time is less than the specified one
                .time(Matchers.lessThan(6000L))
                // validating the value from the body with hamcrest
                .body("name", equalTo("Tech Global"))
                // validating the response content type
                .contentType((ContentType.JSON))
                .extract().response();


        System.out.println("=============Deleting the user==========");
        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().delete("/public/v2/users/" + goRestId)
                .then().log().all()
                .and().assertThat().statusCode(204)
                .time(Matchers.lessThan(6000L))
                .extract().response();
    }
}

