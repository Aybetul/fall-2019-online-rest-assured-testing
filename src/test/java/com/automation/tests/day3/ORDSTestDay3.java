package com.automation.tests.day3;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class ORDSTestDay3 {
    @BeforeAll
    public static void setup(){
        baseURI = "http://54.224.118.38:1000/ords/hr";
    }
    /**
     * given resource path is "/regions/{id}"
     * when user makes get request
     * and region id is equals to 1
     * then assert that status code is 200
     * and assert that region name is Europe
     * and assert that region id is 1
     */
    @Test
    public void verifyFirstRegion(){
        given().
                pathParam("id", 1).
                when().
                get("/regions/{id}").prettyPeek().
                then().assertThat().
                statusCode(200).
                body("region_name", is("Europe")).
                body("region_id", is(1)).
                time(lessThan(5L), TimeUnit.SECONDS); //verify that response time is less than 5 seconds
    }


    @Test
    public void verifyEmployees(){
      Response response=given().accept(ContentType.JSON).when().get("/employees").prettyPeek();
        JsonPath jsonPath=response.jsonPath();
        String nameOfFirstEmployees= jsonPath.getString("items[0].first_name");
        String nameOfLastEmployees= jsonPath.getString("items[-1].first_name");//-1  last item in he array
        String nameOfBeforeLastEmployees= jsonPath.getString("items[-2].first_name");


        System.out.println("nameOfFirstEmployees = " + nameOfFirstEmployees);
        System.out.println("nameOfLastEmployees = " + nameOfLastEmployees);
        System.out.println("nameOfBeforeLastEmployees = " + nameOfBeforeLastEmployees);

        Map<String,Object> firstEmployee= jsonPath.get("items[0]");
        Map<String,?> secondEmployee= jsonPath.get("items[1]");
    }
}