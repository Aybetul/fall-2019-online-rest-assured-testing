package com.automation.APIShorts;

import com.automation.utilities.ConfigurationReader;
import static  io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SpartansTest {
    String BaseURI= ConfigurationReader.getProperty("SPARTAN.URI");

   @Test
   public void viewSpartanTest1(){
Response response=given().auth().basic("admin", "admin").get(BaseURI+"/spartans").prettyPeek();
      // System.out.println(response.body().prettyPrint());
response.then().assertThat().statusCode(200); // assertion for status code

   }


/*
GET request for spartans
ythen status code 200
And body contains Mike
 */
@Test
    public void viewSpartans2(){
Response response= given().auth().basic("admin", "admin").
        get(BaseURI+"/Spartans").prettyPeek();
       response. then().assertThat().statusCode(200);
 Assertions.assertTrue(response.body().asString().contains("Mike"));

}
@Test
    public void viewSpartans3(){
   Response response= given().auth().basic("admin", "admin").
           accept(ContentType.JSON).
           when().get(BaseURI+"/spartans");
          response. then().assertThat().statusCode(200).
           and().assertThat().contentType(ContentType.JSON);

    }

@Test
    public void partParams(){
 Response response=   given().auth().basic("admin", "admin").
         accept(ContentType.JSON).
         when().get(BaseURI+"/spartans/{id}",340).prettyPeek();

//response.then().assertThat().statusCode(200).and().contentType(ContentType.JSON);
//response.getBody().toString().contains("dil" );

}
}
