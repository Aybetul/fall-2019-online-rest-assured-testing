package com.automation.tests.day9;


import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class BadSSL {



    /**
     * no valid certificate - no handshake, no secure connection
     *     relaxedHTTPSValidation - ignore SSL certificate issues
     *      * Use relaxed HTTP validation with SSLContext protocol SSL.
     *      This means that you'll trust all hosts regardless if the SSL certificate is invalid.
     *      By using this method you don't need to specify a keystore or trust store.
     */
  @Test
  public void ConnectBadssl(){
      baseURI="https://untrusted-root.badssl.com/";
      Response res=given().relaxedHTTPSValidation().get().prettyPeek();
  }





}
