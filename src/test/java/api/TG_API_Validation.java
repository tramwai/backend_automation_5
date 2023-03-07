package api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class TG_API_Validation {
    public static void main(String[] args) {

        // POST request - Creating the post request to post the specific user
        Response response;
        Faker faker = new Faker();
        response = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \""+ faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"dob\": \"2000-06-12\"\n" +
                        "}")
                .when().post("https://tech-global-training.com/students")
                .then().log().all().extract().response();


        // GET request - Creating the get request to get the specific user
        int studentID = response.jsonPath().getInt("id");
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .when().get("https://tech-global-training.com/students/" + studentID)
                .then().log().all().extract().response();

        // PUT request - Creating the put request to put the specific user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \""+ faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"dob\": \"2000-06-12\"\n" +
                        "}")
                .when().put("https://tech-global-training.com/students/" + studentID)
                .then().log().all().extract().response();

        // PATCH request - Creating the patch request to patch the specific user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "}")
                .when().put("https://tech-global-training.com/students/" + studentID)
                .then().log().all().extract().response();

        // DELETE request - Creating the get request to delete the specific user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .when().delete("https://tech-global-training.com/students/" + studentID)
                .then().log().all().extract().response();
    }
}
