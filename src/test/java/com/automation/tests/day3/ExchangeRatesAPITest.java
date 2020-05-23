package com.automation.tests.day3;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRatesAPITest {

 @BeforeAll
 public static void setup(){
     baseURI="https://api.openrates.io";

 }
@Test
    public void getLatestRates(){

     Response response=given().
             queryParam("base","USD").when().
             get("/latest").prettyPeek();

    Headers headers= response.getHeaders(); // all headers
    String contentType=headers.getValue("Content-Type");
    System.out.println(contentType);

    //verify that get request was succesfull
     response.then().assertThat().statusCode(200);
 //    response.then().assertThat().body("base",is("USD") );
  //  response.then().assertThat().body("base",equalTo("USD") );
  //  response.then().assertThat().body("base",is("usd".toUpperCase()) );
   // assertTrue(response.body().asString().contains("USD"));

// This line returns date
 String date=  LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    response.then().assertThat().body("date",containsString(date));
}

// get history of rates for 2008
    @Test
    public void getHistoryOfRates(){
     Response response= given().queryParam("base", "USD").
             when().get("/2008-01-02").prettyPeek();
     Headers headers=response.getHeaders();//response header
        response.then().
                statusCode(200).
                and(). // doesn't need it but syntax sugar
                body("date", is("2008-01-02")).// we can chain assertions
                and().body("rates.USD",is(1.0f))
        ;

        Float param =response.jsonPath().get("rates.USD");

        System.out.println(param);
    }

}
