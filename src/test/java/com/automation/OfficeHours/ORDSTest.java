package com.automation.OfficeHours;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import groovy.transform.SelfType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ORDSTest {


/*
get all the employees and their depart ids.
verify that department id points to the existing record in the departmetns table
verify response 200
verify department name is not empty
 */

 /*
    get all the employees and their depart ids.
    verify that department id points to the existing record in the departmetns table
    verify response 200
    verify department name is not empty
     */


    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://54.146.89.247:1000/ords/hr/";
    }

        /*
       get all the records from the employees table using the /employees
       verify that number of employees is more than 100
   ​
        */


    @Test
    public void employeesTest() {
        // turns out you can only get 25 employees per response.
        // need to use extra query parameter to get all at once
        // row_count
        Response response = given().
                queryParam("limit", 110).
                when().
                get("/employees");
        response.
//                prettyPeek().
        then().statusCode(200);

        // get all the employee into a list of maps. each map represents one employee
        List<Map<String, Object>> employees = response.jsonPath().getList("items");
        System.out.println(employees.size());
        System.out.println(employees.get(1));
        assertThat(employees.size(), greaterThan(100));
    }
    @Test
    public void departamentes(){
        List<Integer> depIds = given().
                queryParam("limit", 110).
                when().
                get("/employees").jsonPath().getList("items.department_id");
        System.out.println(depIds);
        // remove duplicates
        // Set<Integer>uniqueDepIds=new HashSet<>(depIds);

        Set<Integer> uniqueDepIds = new HashSet<>();
        uniqueDepIds.addAll(depIds);
        System.out.println(uniqueDepIds);

        // get each separately
        for (Integer depId : uniqueDepIds) {
            // call the department/:id to get the specific department
            // verify 200, verify name is not null
            given().
                    pathParam("id", depId).
                    when().
                    get("/departments/{id}").
                    prettyPeek().
                    then().statusCode(200).and().body("department_name", not(emptyOrNullString()));
        }

    }





}
