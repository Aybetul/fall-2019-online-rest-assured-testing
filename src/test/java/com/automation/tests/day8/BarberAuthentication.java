package com.automation.tests.day8;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class BarberAuthentication {

     @BeforeAll
  public static void setup(){
      baseURI = "https://cybertek-reservation-api-qa.herokuapp.com/";
  }
    @Test
    public void loginTest(){
      Response response= given().
                queryParam("email", "teacherva5@gmail.com").
                queryParam("password", "maxpayne").
                when().
                get("/sign").prettyPeek();

        String access_token = response.jsonPath().getString("accessToken");
        System.out.println("Access Token is: "+access_token);
    }

@Test
@DisplayName("negative test:check if the system gives the info when don't privide authentication")
    public void getRooms(){
         // status code 422 but we should get 401 unauthorized
         get("/api/rooms").prettyPeek().then().statusCode(401);
}

    @Test
    public void getRoomsTest2() {
        //1. Request: to get a token.
        Response response = given().
                queryParam("email", "teacherva5@gmail.com").
                queryParam("password", "maxpayne").
                when().
                get("/sign");
        response.then().log().ifError();
        String token = response.jsonPath().getString("accessToken");
        Response response2 = given().
                auth().oauth2(token).
                when().
                get("/api/rooms").prettyPeek();
    }
    public String getToken() {
        Response response = given().
                queryParam("email", "teacherva5@gmail.com").
                queryParam("password", "maxpayne").
                when().
                get("/sign");
        response.then().log().ifError();
        String token = response.jsonPath().getString("accessToken");
        System.out.println("Token :: " + token);
        return token;
    }

    // get token
    public String getToken(String email, String password) {
        Response response = given().
                queryParam("email", email).
                queryParam("password", password).
                when().
                get("/sign");
        response.then().log().ifError();
        String token = response.jsonPath().getString("accessToken");
        System.out.println("Token :: " + token);
        return token;
    }

@Test
    public void getAllTeams(){
    given().auth().oauth2(getToken()).  // header("Authorization", "Bearer "+getToken()).   this is also work
            when().get("/api/teams").prettyPeek().then().statusCode(200);
}




}
