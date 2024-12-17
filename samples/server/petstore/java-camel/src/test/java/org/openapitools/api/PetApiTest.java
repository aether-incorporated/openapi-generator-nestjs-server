/**
* NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.11.0-SNAPSHOT).
* https://openapi-generator.tech
* Do not edit the class manually.
*/
package org.openapitools.api;

import org.junit.jupiter.api.Test;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.springframework.boot.test.context.SpringBootTest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PetApiTest {
    private static final String API_URL = "http://127.0.0.1:8080/api/v1";

    @Test
    public void addPetTestJson() throws Exception {
        String contentType = "application/json";
        String accept = "application/json";
        String url = API_URL + "/pet";
        HttpRequest httpRequest = Unirest.post(url);
        httpRequest = httpRequest.header("Content-Type", contentType);
        httpRequest = httpRequest.header("Accept", accept);
        
        String requestBody = "{ \"photoUrls\" : [ \"photoUrls\", \"photoUrls\" ], \"name\" : \"doggie\", \"id\" : 0, \"category\" : { \"name\" : \"name\", \"id\" : 6 }, \"tags\" : [ { \"name\" : \"name\", \"id\" : 1 }, { \"name\" : \"name\", \"id\" : 1 } ], \"status\" : \"available\" }";
        httpRequest = ((HttpRequestWithBody) httpRequest).body(requestBody).getHttpRequest();
        
        
        HttpResponse<JsonNode> httpResponse = httpRequest.asJson();
        Assertions.assertTrue(httpResponse.getStatus() < 400 || httpResponse.getStatus() == 415);
    }
    
    @Test
    public void addPetTestXml() throws Exception {
        String contentType = "application/xml";
        String accept = "application/xml";
        String url = API_URL + "/pet";
        HttpRequest httpRequest = Unirest.post(url);
        httpRequest = httpRequest.header("Content-Type", contentType);
        httpRequest = httpRequest.header("Accept", accept);
        
        String requestBody = "<Pet> <id>123456789</id> <Category> <id>123456789</id> <name>aeiou</name> </Category> <name>doggie</name> <photoUrls> <photoUrls>aeiou</photoUrls> </photoUrls> <tags> <Tag> <id>123456789</id> <name>aeiou</name> </Tag> </tags> <status>aeiou</status> </Pet>";
        httpRequest = ((HttpRequestWithBody) httpRequest).body(requestBody).getHttpRequest();
        
        
        HttpResponse<String> httpResponse = httpRequest.asString();
        Assertions.assertTrue(httpResponse.getStatus() < 400 || httpResponse.getStatus() == 415);
    }
    @Test
    public void findPetsByStatusTestJson() throws Exception {
        String contentType = "application/json";
        String accept = "application/json";
        String url = API_URL + "/pet/findByStatus";
        HttpRequest httpRequest = Unirest.get(url);
        httpRequest = httpRequest.header("Accept", accept);
        httpRequest = httpRequest.queryString("status", "1");
        
        
        HttpResponse<JsonNode> httpResponse = httpRequest.asJson();
        Assertions.assertTrue(httpResponse.getStatus() < 400 || httpResponse.getStatus() == 415);
    }
    
    @Test
    public void findPetsByTagsTestJson() throws Exception {
        String contentType = "application/json";
        String accept = "application/json";
        String url = API_URL + "/pet/findByTags";
        HttpRequest httpRequest = Unirest.get(url);
        httpRequest = httpRequest.header("Accept", accept);
        httpRequest = httpRequest.queryString("tags", "1");
        
        
        HttpResponse<JsonNode> httpResponse = httpRequest.asJson();
        Assertions.assertTrue(httpResponse.getStatus() < 400 || httpResponse.getStatus() == 415);
    }
    
    @Test
    public void getPetByIdTestJson() throws Exception {
        String contentType = "application/json";
        String accept = "application/json";
        String url = API_URL + "/pet/{petId}";
        HttpRequest httpRequest = Unirest.get(url);
        httpRequest = httpRequest.header("Accept", accept);
        httpRequest = httpRequest.routeParam("petId", "1");
        
        
        HttpResponse<JsonNode> httpResponse = httpRequest.asJson();
        Assertions.assertTrue(httpResponse.getStatus() < 400 || httpResponse.getStatus() == 415);
    }
    
    @Test
    public void getPetByIdTestXml() throws Exception {
        String contentType = "application/xml";
        String accept = "application/xml";
        String url = API_URL + "/pet/{petId}";
        HttpRequest httpRequest = Unirest.get(url);
        httpRequest = httpRequest.header("Accept", accept);
        httpRequest = httpRequest.routeParam("petId", "1");
        
        
        HttpResponse<String> httpResponse = httpRequest.asString();
        Assertions.assertTrue(httpResponse.getStatus() < 400 || httpResponse.getStatus() == 415);
    }
    @Test
    public void updatePetTestJson() throws Exception {
        String contentType = "application/json";
        String accept = "application/json";
        String url = API_URL + "/pet";
        HttpRequest httpRequest = Unirest.put(url);
        httpRequest = httpRequest.header("Content-Type", contentType);
        httpRequest = httpRequest.header("Accept", accept);
        
        String requestBody = "{ \"photoUrls\" : [ \"photoUrls\", \"photoUrls\" ], \"name\" : \"doggie\", \"id\" : 0, \"category\" : { \"name\" : \"name\", \"id\" : 6 }, \"tags\" : [ { \"name\" : \"name\", \"id\" : 1 }, { \"name\" : \"name\", \"id\" : 1 } ], \"status\" : \"available\" }";
        httpRequest = ((HttpRequestWithBody) httpRequest).body(requestBody).getHttpRequest();
        
        
        HttpResponse<JsonNode> httpResponse = httpRequest.asJson();
        Assertions.assertTrue(httpResponse.getStatus() < 400 || httpResponse.getStatus() == 415);
    }
    
    @Test
    public void updatePetTestXml() throws Exception {
        String contentType = "application/xml";
        String accept = "application/xml";
        String url = API_URL + "/pet";
        HttpRequest httpRequest = Unirest.put(url);
        httpRequest = httpRequest.header("Content-Type", contentType);
        httpRequest = httpRequest.header("Accept", accept);
        
        String requestBody = "<Pet> <id>123456789</id> <name>doggie</name> <photoUrls> <photoUrls>aeiou</photoUrls> </photoUrls> <tags> </tags> <status>aeiou</status> </Pet>";
        httpRequest = ((HttpRequestWithBody) httpRequest).body(requestBody).getHttpRequest();
        
        
        HttpResponse<String> httpResponse = httpRequest.asString();
        Assertions.assertTrue(httpResponse.getStatus() < 400 || httpResponse.getStatus() == 415);
    }
}