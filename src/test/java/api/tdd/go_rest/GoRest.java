package api.tdd.go_rest;

import api.pojo_classes.go_rest.CreateGoRestUser;
import api.pojo_classes.go_rest.UpdateGoRestUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;
import org.hamcrest.Matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class GoRest {

    Response response;
    static Logger logger = LogManager.getLogger(GoRest.class);
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
    public void goRestCRUD() throws JsonProcessingException {
        // Creating a Bean object (POJO)
        CreateGoRestUser createGoRestUser = new CreateGoRestUser();
        // assigned the values to the attributes
        createGoRestUser.setName("Tech Global");
        createGoRestUser.setGender("male");
        createGoRestUser.setEmail(faker.internet().emailAddress());
        createGoRestUser.setStatus("active");

        System.out.println("=========Creating the user with POST request=============");
        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createGoRestUser))
                //.when().post("https://gorest.co.in/public/v2/users")
                .when().post("/public/v2/users")
                .then().log().all()
                // validating the status code with rest assured
                .and().assertThat().statusCode(201)
                // validating the response time is less than the specified one
                .time(Matchers.lessThan(20000L))
                // validating the value from the body with hamcrest
                .body("name", equalTo("Tech Global"))
                // validating the response content type
                .contentType((ContentType.JSON))
                .extract().response();

        // expected status
        String expectedStatusCode = createGoRestUser.getStatus();
        // actual status
        String actualStatusCode = JsonPath.read(response.asString(), "status");
        // debug
        logger.debug("The actual code should be " + expectedStatusCode, "but we found " + actualStatusCode);
        //hamcrest
        assertThat(
               "Checking if the status codes match",
               actualStatusCode,
               is(expectedStatusCode)
        );

        System.out.println("=========Fetching the user with GET request=============");
        expectedGoRestID = response.jsonPath().getInt("id");
        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().get("/public/v2/users/" + expectedGoRestID)
                .then().log().all()
                //validating the status code with rest assured
                .and().assertThat().statusCode(200)
                //validating the response time is less than the specified one
                .time(Matchers.lessThan(20000L))
                //validating the value from the body with hamcrest
                .body("name", equalTo("Tech Global"))
                //validating the response content type
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("=========Updating the user with PUT request=============");

        //createGoRestUser.setName("TechGlobal");

        UpdateGoRestUser updateGoRestUser = new UpdateGoRestUser();
        updateGoRestUser.setName("TechGlobal");
        updateGoRestUser.setEmail(faker.internet().emailAddress());
        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updateGoRestUser))
                .when().put("/public/v2/users/" + expectedGoRestID)
                .then().log().all()
                //validating the status code with rest assured
                .and().assertThat().statusCode(200)
                //validating the response time is less than the specified one
                .time(Matchers.lessThan(20000L))
                //validating the value from the body with hamcrest
                .body("name", equalTo("TechGlobal"))
                //validating the response content type
                .contentType(ContentType.JSON)
                .extract().response();

        expectedGoRestName = updateGoRestUser.getName();
        expectedGoRestEmail = updateGoRestUser.getEmail();
        expectedGoRestGender = createGoRestUser.getGender();
        expectedGoRestStatus = createGoRestUser.getStatus();

        // "id", "name", "email" in the getInt() is the name of the attribute in the response body
        int actualGoRestId = response.jsonPath().getInt("id");
        String actualGoRestName = response.jsonPath().getString("name");
        String actualGoRestEmail = response.jsonPath().getString("email");
        String actualGoRestGender = response.jsonPath().getString("gender");
        String actualGoRestStatus = response.jsonPath().getString("status");

        Assert.assertEquals(actualGoRestId, expectedGoRestID);
        Assert.assertEquals(actualGoRestName, expectedGoRestName);
        Assert.assertEquals(actualGoRestEmail, expectedGoRestEmail);
        Assert.assertEquals(actualGoRestGender, expectedGoRestGender);
        Assert.assertEquals(actualGoRestStatus, expectedGoRestStatus);


        System.out.println("=========Deleting the user with DELETE request=============");
        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().delete("/public/v2/users/"+ expectedGoRestID)
                .then().log().all()
                .and().assertThat().statusCode(204)
                .time(Matchers.lessThan(20000L))
                .extract().response();
    }
}

