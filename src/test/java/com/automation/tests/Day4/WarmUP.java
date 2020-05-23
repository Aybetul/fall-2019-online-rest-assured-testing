package com.automation.tests.Day4;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class WarmUP {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("ORDS.URI");
    }


    /**
     * Warmup!
     * Given accept type is JSON
     * When users sends a GET request to “/employees”
     * Then status code is 200
     * And Content type is application/json
     * And response time is less than 3 seconds
     */

@Test
@DisplayName("Verify status code, content type, response time")
public void employeesTest1(){
    given().
            accept(ContentType.JSON).when().get("/employees").prettyPeek().
            then().
            assertThat().
            statusCode(200).
            contentType(ContentType.JSON).
            time(lessThan(3L), TimeUnit.SECONDS);
}
    /**
     *
     Given accept type is JSON
     And parameters: q = {"country_id":"US"}
     When users sends a GET request to "/countries"
     Then status code is 200
     And Content type is application/json
     And country_name from payload is "United States of America"
     *
     */
@Test
    @DisplayName("Verify country name is US")
    public void GetCountriesVerifyUsa(){

given().
        accept(ContentType.JSON).
        queryParam("q", "{\"country_id\":\"US\"}").
        when().get("/countries").prettyPeek().
        then().
        assertThat().statusCode(200).contentType(ContentType.JSON).
        body("items[0].country_name",is("United States of America"));

// sECOND Request

  Response response= given().accept(ContentType.JSON).when().get("/countries").prettyPeek();

  String countryname=response.jsonPath().getString("items.find{it.country_id=='US'}.country_name"); // will get county=ry name
  Map<String, Object> countryUS=response.jsonPath().get("items.find{it.country_id=='US'}");// will get Us object

    // get all the country name from region 2
List<String> countryNames= response.jsonPath().getList("items.findAll{it.region_id==2}.country_name");

    System.out.println(countryname);
    System.out.println(countryUS);
    System.out.println(countryNames);

    for(Map.Entry<String, Object>entry :countryUS.entrySet()){
        System.out.printf("key:%s , Value =%s \n",entry.getKey(),entry.getValue());
    }

}

//employee with highest salary
@Test
public void getEmployeeTest() {
    Response response = when().get("/employees").prettyPeek();
    //collectionName.max{it.propertyName}
    Map<String, ?> bestEmployee = response.jsonPath().get("items.max{it.salary}");
    Map<String, ?> poorGuy = response.jsonPath().get("items.min{it.salary}");
    int companysPayroll = response.jsonPath().get("items.collect{it.salary}.sum()");
    System.out.println(bestEmployee);
    System.out.println(poorGuy);
    System.out.println("Company's payroll: " + companysPayroll);
}

    /**
     * given path parameter is “/employees”
     * when user makes get request
     * then assert that status code is 200
     * Then user verifies that every employee has positive salary
     *
     */
    @Test
    @DisplayName("Verify that every employee has positive salary")
    public void testSalary(){
        when().
                get("/employees").
                then().assertThat().
                statusCode(200).
                body("items.salary", everyItem(greaterThan(0))).
                log().ifError();
    }


}
