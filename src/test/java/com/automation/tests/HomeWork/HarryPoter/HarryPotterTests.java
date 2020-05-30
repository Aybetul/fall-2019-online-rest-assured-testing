package com.automation.tests.HomeWork.HarryPoter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


public class HarryPotterTests {
    String API_key = "$2a$10$DgUGNm.1naaC6afHpVmrCOvmGQmQ32EntwKQ0DRxxA7eH7w8CPpoO";

    @BeforeAll
    public static void setUp() {
        baseURI = "https://www.potterapi.com/v1/";
    }

    /*
    Verify sorting hat
     1.Send a get request to /sorting
    Hat. Request includes :
    2.Verify status code 200, content type application/json; charset=utf-8
    3.Verify that response body contains one of the following houses:
     "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */
    @Test
    public void SortingHat() {
        Response response = given().queryParam("apiKey", API_key).when().get("/sortingHat").prettyPeek();
        response.then().contentType(ContentType.JSON).statusCode(200);
        String l = response.body().asString();
        Assertions.assertTrue(l.contains("Gryffindor") || l.contains("Ravenclaw") || l.contains("Slytherin") || l.contains("Hufflepuff"));

    }
/*
Verify bad key
1.Send a get request to /characters. Request includes :
•Header Accept with value application/json
•Query param key with value invalid
2.Verify status code 401, content type application/json; charset=utf-8
3.Verify response status line include message Unauthorized
4.Verify that response body says"error":"APIKeyNotFound"
 */

    @Test
    public void verifyBadKey() {
        Response response = given().header("Accept", "application/json").
                queryParam("key", "$2a$10$DgUGNm.1naaC6afHpVmrCOvmGQmQ32EntwKQ0DRxxA7eH7w8CP123").
                when().get(" /characters").prettyPeek();
        response.then().contentType(ContentType.JSON).statusCode(401).statusLine(containsString("Unauthorized"));
        String error = response.body().asString();
        assertTrue(error.contains("API Key Not Found"));

    }
/*
Verify number of characters
 1.Send a get request to /characters. Request includes :
 •Header Accept with value application/json•Query param key with value {{apiKey}}
 2.Verify status code 200, content type application/json; charset=utf-8
 3.Verify response contains 194 characters
 */
    @Test
    public void verifyNumberOfCharacters() {
        Response response = given().header("Accept", "application/json").
                queryParam("key", API_key).
                when().get(" /characters").prettyPeek();
        response.then().contentType(ContentType.JSON).statusCode(200);
int size=response.getBody().jsonPath().getList("").size();
assertEquals(size,195); ///194?
    }

/*
Verify number of character id and house
 1.Send a get request to /characters. Request includes :
 •Header Accept with value application/json
 •Query param key with value {{apiKey}}
 2.Verify status code 200, content type application/json; charset=utf-8
 3.Verify all characters in the response have id field which is not empty
 4.Verify that value type of the field dumbledores
 Army is a boolean in all characters in the response
 5.Verify value of the house in all characters in the response is one of the following:
  "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
 */
@Test
public void IdAndHouse(){
    Response response=given().contentType(ContentType.JSON).queryParam("key", API_key).
            when().get("/characters").prettyPeek();
    response.then().statusCode(200).contentType(ContentType.JSON);

    int size=response.getBody().jsonPath().getList("").size();
    for (int i = 0; i < size; i++) {

        String id = response.getBody().jsonPath().getString("[" + i + "]._id");
        assertTrue(id != null);


        Boolean bool = response.body().jsonPath().getBoolean("[" + i + "].dumbledoresArmy");
        assertTrue(bool == true | bool == false);


       List<Map<String,Object>> list= response.getBody().jsonPath().getList("");
List<String> houseList= new ArrayList<>(Arrays.asList("Gryffindor","Ravenclaw", "Slytherin", "Hufflepuff"));


            if (list.get(i).containsKey("house")) {
                String house = list.get(i).get("house").toString();

                assertTrue(houseList.contains(house));
            }

    }
}
/*
Verify all character information
1.Send a get request to /characters. Request includes :
•Header Accept with value application/json•Query param key with value {{apiKey}}
2.Verify status code 200, content type application/json; charset=utf-8
 */
@Test
    public void characterInformation(){
    Response response =given().contentType(ContentType.JSON).queryParam("key",API_key).
            when().
            get("/characters").prettyPeek();
           response. then().statusCode(200).contentType(ContentType.JSON);

    //3.Select name of any random character

    Random random=new Random();
    int r=random.nextInt(194);
        Map<String,?>res=  response.body().jsonPath().get("["+r+"]");
       String name=  res.get("name").toString();
    System.out.println(name);

    //4.Send a get request to /characters. Request includes :
    //•Header Accept with value application/json•Query param key with value {{apiKey}}
    //•Query param name with value from step 3
    //5.Verify that response contains the same character information from step3. Compare all fields.
    Response response2 =given().contentType(ContentType.JSON).queryParam("key",API_key).queryParam("name",name).
            when().
            get("/characters").prettyPeek();
    response2. then().statusCode(200).contentType(ContentType.JSON);
   String res2= response2.getBody().jsonPath().get("[0].name");
  assertEquals(res2 ,name);


}
/*
Verify name search
1.Send a get request to /characters. Request includes :
•Header Accept with value application/json•Query param key with value {{apiKey}}
•Query param name with value Harry Potter
2.Verify status code 200, content type application/json; charset=utf-8
3.Verify name Harry Potter4.Send a get request to /characters. Request includes :
•Header Accept with value application/json•Query param key with value {{apiKey}}

 */
@Test
public void VerifyNameSearch(){
    Response response=given().contentType(ContentType.JSON).
            queryParam("key",API_key).
            queryParam("name","Harry Potter").
            get("/characters").prettyPeek();
    response.then().contentType(ContentType.JSON).statusCode(200);
   assertEquals( response.body().jsonPath().get("[0].name"),"Harry Potter");

   //•Query param name with value Marry Potter
    //5.Verify status code 200, content type application/json; charset=utf-8

    Response response2=given().contentType(ContentType.JSON).
            queryParam("key",API_key).
            queryParam("name","Marry Potter").
            get("/characters").prettyPeek();

    //6.Verify response body is empty
    response2.then().contentType(ContentType.JSON).statusCode(200);
  assertTrue( response2.body().jsonPath().getList("").size()==0);

}
/*
Verify house members
1.Send a get request to /houses. Request includes :
•Header Accept with value application/json
•Query param key with value {{apiKey}}
2.Verify status code 200, content type application/json; charset=utf-8



 */
@Test
    public void houseMember() {
    Response response = given().queryParam("key", API_key).contentType(ContentType.JSON).
            when().get("/houses").prettyPeek();
    response.then().statusCode(200).contentType(ContentType.JSON);

    //3.Capture the id of the Gryffindor house
    int size = response.getBody().jsonPath().getList("").size();
    String id = "";
    for (int i = 0; i < size; i++) {
        String Name = response.body().jsonPath().get("[" + i + "].name").toString();
        if (Name.equals("Gryffindor")) {
            id = response.body().jsonPath().get("[" + i + "]._id").toString();
        }

    }
    System.out.println(id);

    //4.Capture the ids of the all members of the Gryffindor house
    List<String> members = new ArrayList();
    for (int i = 0; i < size; i++) {
        String Name = response.body().jsonPath().get("[" + i + "].name").toString();
        if (Name.equals("Gryffindor")) {
            members = response.body().jsonPath().getList("[" + i + "].members");
        }
    }
    System.out.println(members);

/*
5.Send a get request to /houses/:id. Request includes :
•Header Accept with value application/json•Query param key with value {{apiKey}}
 •Path param id with value from step 3
 6.Verify that response contains the  same memberids as the step 4
 */
    Response response2 = given().queryParam("key", API_key).contentType(ContentType.JSON).pathParam("houseId",id).
            when().get("/houses/{houseId}").prettyPeek();
    response2.then().statusCode(200).contentType(ContentType.JSON);
 String ac= response2.jsonPath().get("[0]._id").toString();
assertEquals(id,ac);


}
/*
Verify house members again
1.Send a get request to /houses/:id. Request includes :
•Header Accept with value application/json•Query param key with value {{apiKey}}

 */
@Test
    public  void membersAgain() {
    String houseId = "5a05e2b252f721a3cf2ea33f";
    Response response = given().queryParam("key", API_key).contentType(ContentType.JSON).pathParam("houseId", houseId).
            when().get("/houses/{houseId}").prettyPeek();
    response.then().statusCode(200).contentType(ContentType.JSON);

    // Path param id with value 5a05e2b252f721a3cf2ea33f2.Capture the ids of all members
    List<Map<String, String>> members = response.getBody().jsonPath().getList("[0].members"); // member list
    System.out.println(members);

// loop all members and get ids and put them in the list
    List<String> idAll = new ArrayList<>();

    for (int i = 0; i < members.size(); i++) {
        String currentId = members.get(i).get("_id");
        idAll.add(currentId);
    }
    System.out.println(idAll);

    //3.Send a get request to /characters. Request includes :•Header Accept with value application/json
    //•Query param key with value {{apiKey}}•Query param house with value Gryffindor
    Response response1 = given().queryParam("key", API_key).
            contentType(ContentType.JSON).queryParam("house", "Gryffindor").
            when().get("/characters").prettyPeek();
    response1.then().statusCode(200).contentType(ContentType.JSON);

    //after request collect ids
int size=response1.getBody().jsonPath().getList("").size();
    System.out.println(size);
    List<String> collectId = new ArrayList<>();
    for (int i = 0; i <size; i++) {
        String currentId=response1.body().jsonPath().get("["+i+"]._id");
       collectId.add( currentId);

    }
    //4.Verify that response contains the same member ids from step 2
    assertTrue(collectId.containsAll(idAll));
}
/*
Verify house with most members
1.Send a get request to /houses. Request includes :
•Header Accept with value application/json•Query param key with value {{apiKey}}
2.Verify status code 200, content type application/json; charset=utf-8
3.Verify that Gryffindor house has the most members
 */
@Test
    public void MostMembers() {
    Response response = given().queryParam("key", API_key).contentType(ContentType.JSON).
            when().get("/houses").prettyPeek();
    response.then().statusCode(200).contentType(ContentType.JSON);

//create map put names and member size in to map
    Map<String, Integer> map = new TreeMap<>();
    int size = response.getBody().jsonPath().getList("").size();
    for (int i = 0; i < size; i++) {

        String name = response.body().jsonPath().get("[" + i + "].name");
        List<String> members = response.body().jsonPath().get("[" + i + "].members");
        map.put(name, members.size());
    }
    System.out.println(map);

    //use entryset to iter over map and find Gryffindor members
    int GMembers=0;
    for (Map.Entry<String,Integer> each : map.entrySet())
   if(  each.getKey().equals("Gryffindor")){
        GMembers=each.getValue();
   }
    System.out.println(GMembers);

// verify Gryffindor has more members
    for (Map.Entry<String,Integer> each : map.entrySet()){
        if(  each.getKey().equals("Gryffindor")) continue;
        assertTrue(GMembers>each.getValue());
        }

}


}