package org.truelayer.challenge.apiSupport;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.truelayer.challenge.util.Log;
import java.util.Map;

public class RestApiCalls {
    public Response getResponseWithQueryParams(String endPoint, Map<String, String> map) {
        Response response = RestAssured.given().
                when().
                queryParams(map).
                get(endPoint).
                thenReturn();
        int statusCode = response.statusCode();
        String body = response.getBody().asString();
        if (statusCode != 200) {
            Log.startLogging();
            Log.Logging("Get Request With Full Response");
            Log.Logging("The Response Body is    " + body);
            Log.Logging("The Endpoint    " + endPoint + "  with query params  " + map +"    returns    " + statusCode);
            Log.endLogging();
        }
        return response;
    }
}
