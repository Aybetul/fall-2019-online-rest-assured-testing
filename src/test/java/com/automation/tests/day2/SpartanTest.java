package com.automation.tests.day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.Random;

import static io.restassured.RestAssured.*;





public class SpartanTest {
    String BASE_URL="http://54.224.118.38:8000";

    //URI (Uniform Resource Identifier) = URL + URN = http://www.google.com/index.html
    //URL (Uniform Resource Locator)    = http://www.google.com
    //URN (Uniform Resource Name)       = /index.html

    @Test
    @DisplayName("get list of all spartans")
    public void getAllSpartans(){
                given().
                       auth().basic("admin","admin").baseUri(BASE_URL).
                when().
                        get("/api/spartans").prettyPeek().
                then().
                        statusCode(200);
        // will return 401 -unauthorized
       // we need to provide credentials.
    }


@Test
@DisplayName("Add new Spartan")
public void addNewSpartan(){
    String body = "{\"gender\": \"Male\", \"name\": \"Random User\", \"phone\": 99999999999}";
    File jsonFile= new File(System.getProperty("user.dir")+"/spartan.json");
           given().
              contentType(ContentType.JSON).
              auth().basic("admin", "admin").
               body(jsonFile).
               baseUri(BASE_URL).
           when().
              post("/api/spartans").prettyPeek().
           then().
              statusCode(201);
}
@Test
@DisplayName("delete a spartan with id")
public void deleteSpartanTest() {
        given().
                auth().basic("admin", "admin").baseUri(BASE_URL).
        when().
                delete("/api/spartans/{id}", 179).prettyPeek().
        then().
                statusCode(204);

    }




}
