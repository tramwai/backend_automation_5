package api.tdd.pet_store;

import api.pojo_classes.pet_store.AddAPet;
import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.Tags;
import api.pojo_classes.pet_store.UpdateAPet;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AddPetToStoreWithLombok {

    static Logger logger = LogManager.getLogger(AddPetToStoreWithLombok.class);

    Response response;

    @BeforeSuite
    public void testStarts(){
        logger.info("Starting the test suite");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("Starting the API test");
        // by having RestAssured URI set implicitly into the rest assured
        // we just add a path to the post call
        RestAssured.baseURI = ConfigReader.getProperty("PetStoreBaseURI");
    }


    @Test
    public void addPetToStore(){

        // build the body from AddAPet
        Category category = Category
                .builder()
                .id(10)
                .name("horse")
                .build();

        Tags tags0 = Tags
                .builder()
                .id(15)
                .name("unicorn")
                .build();


        Tags tags1 = Tags
                .builder()
                .id(16)
                .name("pearl")
                .build();

        AddAPet addAPet = AddAPet
                .builder()
                .id(8)
                .category(category)
                .name("rainbow")
                .photoUrls(Arrays.asList("My horse's Photo URL"))
                .tags(Arrays.asList(tags0, tags1))
                .status("available")
                .build();


        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(addAPet)
                .when().post("/v2/pet")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();

        // getting the pet id from the response body
        int actualPetId = response.jsonPath().getInt("id");
        int actualTagsId0 = response.jsonPath().getInt("tags[0].id");

        int actualPetIdWithJayway = JsonPath.read(response.asString(), "id");
        logger.info("My id with Jayway is " + actualPetIdWithJayway);

        int actualTagsId0WithJayway = JsonPath.read(response.asString(), "tags[0].id");
        logger.info("My pet tag id with Jayway is " + actualTagsId0WithJayway);

        // getting the pet id from the request body
        int expectedPetId = addAPet.getId();
        int expectedTagsId0 = tags0.getId();

        // we are logging the information
        logger.info("My Actual petId is " + actualPetId);

        // we are debugging the assertion
        logger.debug("The actual pet id should be " + expectedPetId + " but we found this " + actualPetId);

//        Assert.assertEquals(actualPetId, expectedPetId);
//        Assert.assertEquals(actualTagsId0, expectedTagsId0);

        // Assertion with hamcrest
        assertThat(
                // reason why we are asserting
                "I'm checking if the " + expectedPetId + "is matching with the actual " + actualPetIdWithJayway,
                // actual value
                actualPetIdWithJayway,
                // expected value
                is(expectedPetId)
        );


        System.out.println("Update the pet");

        Category updateCategory = Category
                .builder()
                .name("horse")
                .id(11)
                .build();

        UpdateAPet updateAPet = UpdateAPet
                .builder()
                .id(8)
                .category(updateCategory)
                .name("rainbow")
                .photoUrls(Arrays.asList("My horse's Photo URL"))
                .tags(Arrays.asList(tags0, tags1))
                .status("unavailable")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(updateAPet)
                .when().put("/v2/pet")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();


    }
}
