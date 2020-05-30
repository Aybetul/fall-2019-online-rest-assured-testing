package com.automation.tests.day7;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

import java.util.Base64;

public class decodedAuthenticantion {

    public static void main(String[] args) {
        byte[] decoded = Base64.getDecoder().decode("YWRtaW46YWRtaW4=");
        String value = new String(decoded);
        System.out.println(value);


        String a="admin:admin";
        char[] arr=a.toCharArray();
        System.out.println(Byte.parseByte(a));
    //    Base64.getEncoder().encode()

    }
    @Test
    public void athwithEncode(){
        baseURI="http://54.146.89.247:8000";
        given()
                .baseUri(baseURI)
                .header("Authorization", "Basic "+Base64.getEncoder().encodeToString("admin:admin".getBytes()))
                .when()
                .get("/api/spartans/search")
                .prettyPeek();
    }



}
