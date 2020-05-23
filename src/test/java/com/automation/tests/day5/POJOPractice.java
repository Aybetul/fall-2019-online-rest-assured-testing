package com.automation.tests.day5;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class POJOPractice {
    @BeforeAll
    public static void beforeAll(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
    }
    @Test
    public void getUser(){
        Response response =   given().
                auth().
                basic("admin", "admin").
                when().
                get("/spartans/{id}", 393).prettyPeek();

        Spartan spartan= response.as(Spartan.class);
        System.out.println(spartan);

        assertEquals(393,spartan.getId());
        assertEquals("Michael Scott", spartan.getName());
        assertEquals("Male", spartan.getGender());


        Map<String, ?> spartanMap= response.as(Map.class);
        System.out.println(spartanMap);
    }

@Test
    public void addUser(){
        Spartan spartan= new Spartan("Ali Jan", "Male", 3426272789L);
        Gson gson= new Gson();
        String pojoAsJson= gson.toJson(spartan );
    System.out.println(pojoAsJson);


    Response response=given().
            auth().basic("admin", "admin").
            contentType(ContentType.JSON).body(spartan).
            when().
            post("/spartans").prettyPeek(); // add some one

    response.then().statusCode(201); // verify added

    int userId= response.jsonPath().getInt("data.id"); // get new added user id
    System.out.println("user id:: "+userId);

    given().
            auth().basic("admin", "admin").
            when().
            delete("/spartans/{id}",userId).prettyPeek(). // delete user
            then().
            assertThat().statusCode(204); // verify is is deleted
}


}
