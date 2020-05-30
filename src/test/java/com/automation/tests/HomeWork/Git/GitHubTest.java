package com.automation.tests.HomeWork.Git;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



import java.util.*;

public class GitHubTest {
    @BeforeAll
    public static  void setUp(){
        baseURI="https://api.github.com/orgs";
    }
/*
Verify organization information
1.Send a get request to /orgs/:org. Request includes :•Path param org with value cucumber
2.Verify status code 200, content type application/json; charset=utf-8
3.Verify value of the login field is cucumber
4.Verify value of the name field is cucumber
5.Verify value of the id field is 320565
 */
@Test
    public void VerifyOrganization(){
    Response response=given().queryParam("org","cucumber") .
            contentType(ContentType.JSON) .when().get("/:org.")  .prettyPeek();
    response.
            then().
            statusCode(200).contentType(ContentType.JSON).
            assertThat().body("login" ,is("cucumber")).
            assertThat().body("name", is("Cucumber")).
            assertThat().body("id", is(320565));

}


/*
Verify error message
 1.Send a get request to /orgs/:org. Request includes :
 •Header Accept with value application/xml
 •Path param org with value cucumber
 2.Verify status code 415, content type application/json; charset=utf-8
 3.Verify response status line include message Unsupported Media Type
 */

@Test
    public void verifyErrorMessage(){
   Response response=given().header("Accept","application/xml")
           .queryParam("org","cucumber").when().get("/:org").prettyPeek();
   response.then().assertThat().statusCode(415).
           assertThat().contentType(ContentType.JSON).
           assertThat().statusLine(containsString("Unsupported Media Type"));
}

/*
Number of repositories
1.Send a get request to /orgs/:org. Request includes :
•Path param org with value cucumber
2.Grab the value of the field public_repos
3.Send a get request to /orgs/:org/repos. Request includes :
•Path param org with value cucumber
4.Verify that number of objects in the response  is equal to value from step 2
 */
@Test
    public void GetNumberOfRepositories(){

        Response response=given().queryParam("org","cucumber") .
                contentType(ContentType.JSON) .when().get("/:org.")  .prettyPeek();
      int r=  response.body().jsonPath().get("public_repos");
    System.out.println(r);

    Response response1=given().queryParam("org","cucumber").contentType(ContentType.JSON).
            when().get("/:org/repos").prettyPeek();
int size=response1.jsonPath().getList("").size();
   Assertions.assertEquals(r,size);

}
/*
Repository id information
1.Send a get request to /orgs/:org/repos. Request includes :
•Path param org with value cucumber2.Verify that id field is unique in every in every object in the response
3.Verify that node_id field is unique in every in every object in the response
 */
    @Test
    public void RepositoriesIdInfo() {
        Response response = given().queryParam("org", "cucumber").
                contentType(ContentType.JSON).when().get("/:org/repos");
          List<Integer> ids= new ArrayList<>();
          List<String > nodes_id=new ArrayList<>();

        for (int i = 0; i <response.getBody().jsonPath() .getList("").size(); i++) {
            int id=response.jsonPath().getInt("["+i+"].id");
            String nodeid=response.jsonPath().getString("["+i+"].node_id");
            ids.add(id);
           nodes_id.add(nodeid);
            System.out.println(id);
        }
       Set<Integer> idSet= new HashSet<>();
        idSet.addAll(ids);
        Set<String> node_idSet= new HashSet<>();
        node_idSet.addAll(nodes_id);

        Assertions.assertEquals(idSet.size(),ids.size());
        Assertions.assertEquals(node_idSet.size(),nodes_id.size());

    }
   /*
   Repository owner information1.Send a get request to /orgs/:org. Request includes :
   •Path param org with value cucumber2.Grab the value of the field id
   3.Send a get request to /orgs/:org/repos. Request includes :
   •Path param org with value cucumber
   4.Verify that value of the id inside the owner object in every response is equal to value from step 2
    */
@Test
public void OwnerInfo(){
    Response response= given().queryParam("org", "cucumber").
            when().get("/:org.");//prettyPeek();
   int id= response.body().jsonPath().get("id");
    System.out.println(id);

   Response response1=given().queryParam("org","cucumber").when().get("/:org/repos");//prettyPeek();

    for (int i = 0; i < response1.getBody().jsonPath().getList("").size(); i++) {
        Map<String, Object> map = response1.getBody().jsonPath().get("["+i+"].owner");

        System.out.println(map.get("id"));
       Assertions.assertEquals( map.get("id"),id);
    }
/*
Ascending order by full_name sort
1.Send a get request to /orgs/:org/repos. Request includes :
•Path param org with value cucumber
•Query param sort with value full_name
2.Verify that all repositories are listed in alphabetical order based on the value of the field name
 */

}
@Test
    public  void FullNameSort(){
//    Response response= given().
//          .
//            when().get("/:org/repos").prettyPeek();

  Response response=  given().queryParam("sort","full_name").when().get("/cucumber/repos").prettyPeek();
  List<String>fullName=new ArrayList<>();

    for (int i = 0; i <response.getBody().jsonPath().getList("").size() ; i++) {
      String name=  response.getBody().jsonPath().get("["+0+"].full_name");
      fullName.add(name);
    }
    List<String >copy=new ArrayList<>();
    copy.addAll(fullName);
    Collections.sort(copy);
     assertEquals(fullName,copy);
}





}
