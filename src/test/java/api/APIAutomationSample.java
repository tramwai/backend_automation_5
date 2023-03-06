package api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class APIAutomationSample {
    public static void main(String[] args) {

        /**
         * Response is an interface coming from the RestAssured Library
         * The Response variable "response" stores all the components of API calls
         * including the request and response
         * RestAssured is written with BDD flow
         *
         */

        // POST request - Creating the post request to post the specific user
        Response response;
        Faker faker = new Faker();
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 0861459652025fecc18cd1193cdf8fc959f2c80a0429a24513ea5dec78536b63")
                .body("{\n" +
                        "    \"name\": \"" + faker.name().fullName() + "\",\n" +
                        "    \"gender\": \"male\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().post("https://gorest.co.in/public/v2/users")
                .then().log().all().extract().response();
        System.out.println(response.asString());


        // GET request - Creating the get request to get the specific user
        int postId = response.jsonPath().getInt("id");
        System.out.println("Id is coming from response " + postId);
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 0861459652025fecc18cd1193cdf8fc959f2c80a0429a24513ea5dec78536b63")
                .when().get("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();


        // GET all users - Creating the get request to get all user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 0861459652025fecc18cd1193cdf8fc959f2c80a0429a24513ea5dec78536b63")
                .when().get("https://gorest.co.in/public/v2/users/")
                .then().log().all().extract().response();


        // PUT request - Creating the put request to put the specific user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 0861459652025fecc18cd1193cdf8fc959f2c80a0429a24513ea5dec78536b63")
                .body("{\n" +
                        "    \"name\": \"" + faker.name().fullName() + "\",\n" +
                        "    \"gender\": \"male\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().put("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();
        System.out.println(response.asString());


        // PATCH request - Creating the patch request to patch the specific user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 0861459652025fecc18cd1193cdf8fc959f2c80a0429a24513ea5dec78536b63")
                .body("{\n" +
                        "    \"name\": \"" + faker.name().fullName() + "\",\n" +
                        "    \"gender\": \"male\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().put("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();
        System.out.println(response.asString());

        int patchId = response.jsonPath().getInt("id");
        Assert.assertEquals(postId, patchId, "Expected id "+ postId + " we found " + patchId);

        // DELETE request - Creating the get request to delete the specific user
        System.out.println("Id is coming from response " + postId);
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 0861459652025fecc18cd1193cdf8fc959f2c80a0429a24513ea5dec78536b63")
                .when().delete("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();
    }
}
