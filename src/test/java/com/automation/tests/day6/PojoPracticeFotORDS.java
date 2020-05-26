package com.automation.tests.day6;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import groovy.transform.SelfType;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class PojoPracticeFotORDS {

  @BeforeAll
  public static void setup(){
      baseURI=ConfigurationReader.getProperty("ORDS.URI");


  }


  @Test
    public  void getEmployeeTest(){
      Response response= get("/employees/{id}", 100).prettyPeek();
    //  response.
  }


}
