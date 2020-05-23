package com.automation.tests.day2;

import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class ORDSTest {
   String BASE_URL="http://3.90.112.152:1000/ords/hr";

   @Test
   @DisplayName("Get list of all employees")
   public void getAllEmployees(){

      //responce can be saved in the response object
      //PrettyPeek  - methods that prints response in the nice format
      // response contains body , header, status line
      // body(payload)- contains info that we request
      //header -contains meta data
Response response= given().baseUri(BASE_URL).
        when().get("/employees").prettyPeek();


   }
@Test
@DisplayName("Get employyes under specific id")
public void getOneEmployees(){
   Response response=given().baseUri(BASE_URL)
           .when().get("/employees/{id}",100).prettyPeek();

   //how we verify responses ?  use assertion!!
   response.then().statusCode(200);// verify code 200 mean every thing is fine
   int statusCode= response.statusCode(); // status code variable.
   Assertions.assertEquals(200,statusCode);
}

   /**
    * given base URI = http://3.90.112.152:1000/ords/hr
    * when user sends get request to "/countries"
    * then user verifies that status code is 200
    */
@Test
@DisplayName("get all countries  verifies that status code is 200")
   public void getCountries() {
   given().baseUri(BASE_URL).when().get("/countries").prettyPeek().then().statusCode(200);
   // statusLine("OKAY") // statusline is message this part will fail because status line is different

}








}
