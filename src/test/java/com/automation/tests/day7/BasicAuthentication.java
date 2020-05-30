package com.automation.tests.day7;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class BasicAuthentication {


    @Test // user can loging see data
    public  void spartanAuthentation(){
        baseURI= ConfigurationReader.getProperty("SPARTAN.URI");
        given().
                auth().basic("user", "user").
         when().
                get("/spartans").prettyPeek().
         then().
                statusCode(200);
    }


@Test // user : user  can  login but can't edit delete or add data 403 :forbidden access
    public void authorizationTest(){
        Spartan spartan=new Spartan("Metin ","Male",12365478936L);

    baseURI= ConfigurationReader.getProperty("SPARTAN.URI");
    given().
            auth().basic("user", "user").body(spartan).contentType(ContentType.JSON).
     when().
            post("/spartans").prettyPeek().
     then().
            statusCode(403);
    // Authentication problem about log in
    // Authorization problems about you log in but can't do some action

}
    @Test // we did not provide the user name and password status code should be 401
    public void authenticationTest() {

        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        get("/spartans").prettyPeek().
                then().
                statusCode(401);

    }
    @Test
    public void authenticationTest2(){
        baseURI = "http://practice.cybertekschool.com";
        given().
                auth().basic("admin", "admin").
                when().
                get("/basic_auth").prettyPeek().
                then().
                statusCode(200).
                contentType(ContentType.HTML);
    }




}
