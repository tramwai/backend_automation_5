package api.tdd.go_rest;

import api.pojo_classes.go_rest.comments.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;
import io.restassured.response.Response;

import java.util.Arrays;


public class CreateGoRestCommentWithLombok {

    static Logger logger = LogManager.getLogger(CreateGoRestCommentWithLombok.class);

    Response response;

    @BeforeTest
    public void beforeTest() {
        System.out.println("Starting the API test");
        // By having RestAssured URI set implicitly in to rest assured
        // we just add path to the post call
        RestAssured.baseURI = ConfigReader.getProperty("GoRestBaseURI");
    }

    @Test
    public void createGoRestComment(){
        Links links = Links
                .builder()
                .previous("no previous data")
                .current("https://gorest.co.in/public/v1/comments?page=1")
                .next("https://gorest.co.in/public/v1/comments?page=2")
                .build();

        CommentsPagination commentsPagination = CommentsPagination
                .builder()
                .total(2000)
                .pages(2000)
                .page(5)
                .limit(10)
                .links(links)
                .build();

        Meta meta = Meta.builder().pagination(commentsPagination).build();

        CommentData commentData0 = CommentData
                .builder()
                .id(2220)
                .postId(5585)
                .name("Tecch Global")
                .email("random email")
                .body("random text")
                .build();

        CommentData commentData1 = CommentData
                .builder()
                .id(2220)
                .postId(5585)
                .name("Tecch Global")
                .email("random email")
                .body("random text")
                .build();

        CommentData commentData2 = CommentData
                .builder()
                .id(2220)
                .postId(5585)
                .name("Tecch Global")
                .email("random email")
                .body("random text")
                .build();


        GoRestCommentWithLombok goRestCommentWithLombok = GoRestCommentWithLombok
                .builder()
                .code(403)
                .meta(meta)
                .data(Arrays.asList(commentData0, commentData1, commentData2))
                .build();

        response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(goRestCommentWithLombok)
                .when().post("/public-api/comments")
                .then().log().all()
                .extract().response();

    }
}
