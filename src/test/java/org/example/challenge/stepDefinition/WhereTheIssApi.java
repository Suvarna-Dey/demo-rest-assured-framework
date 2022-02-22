package org.example.challenge.stepDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.challenge.apiSupport.RestApiCalls;
import org.example.challenge.pojo.Request.GetSatellitePositionByIdRequest;
import org.example.challenge.pojo.Request.GetTleBySatelliteIdRequest;
import org.example.challenge.pojo.Response.GetSatellitePositionByIdResponse;
import org.example.challenge.pojo.Response.GetTleBySatelliteIdResponse;
import org.example.challenge.util.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;

public class WhereTheIssApi extends RestEndPoints{
    private final RestApiCalls restApiCalls = new RestApiCalls();
    private Response response;
    private ObjectMapper mapper = new ObjectMapper();
    private Map<String,String> query_params = new HashMap<>();
    private GetSatellitePositionByIdRequest getSatelliteByIdRequest = new GetSatellitePositionByIdRequest();
    private GetTleBySatelliteIdRequest getTleBySatelliteIdRequest =new GetTleBySatelliteIdRequest();
    private int serviceUnreliabilityCount;

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int responseCode) {
        assertEquals("Response code mismatch, errorMessage: "+response.getBody().asString(), responseCode, response.getStatusCode());
    }

    @Then("the response body should be have satellite position")
    public void the_response_body_should_be_have_satellite_position() throws JsonProcessingException {
        GetSatellitePositionByIdResponse[] getSatellitePositionResponse =  mapper.readValue(response.asString(), GetSatellitePositionByIdResponse[].class);

        //Check if total positions returned are equal to unique number of timestamps
        List<String> timestamps = Arrays.asList(query_params.get("timestamps").split(","));
        Set<String> unique_time = new HashSet<>(timestamps);
        assertEquals("Number of positions returned do not match the timestamps",unique_time.size(),getSatellitePositionResponse.length);

        //response validation by soft assertion
        for(GetSatellitePositionByIdResponse resultJson:getSatellitePositionResponse)
        {
            assertThat(resultJson.getName()).isNotNull();
            assertThat(String.valueOf(resultJson.getId())).isEqualTo(getSatelliteByIdRequest.getSatelliteId());
            assertThat(resultJson.getLatitude()).isNotNull();
            assertThat(resultJson.getLongitude()).isNotNull();
            assertThat(resultJson.getAltitude()).isNotNull();
            assertThat(unique_time.contains(resultJson.getTimestamp())).isTrue();
            assertThat(resultJson.getSolar_lat()).isNotNull();
            assertThat(resultJson.getSolar_lon()).isNotNull();
            if (getSatelliteByIdRequest.getUnits()!=null)
                assertThat(resultJson.getUnits()).isEqualTo(getSatelliteByIdRequest.getUnits());
            else
                assertThat(resultJson.getUnits()).isEqualTo("kilometers");
        }
    }

    @When("I get the satellite position for satellite {string} for below")
    public void i_get_the_satellite_position_for_satellite_for_below(String satellite_id, DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        for (List<String> columns : rows) {
            query_params.put(columns.get(0), columns.get(1));
        }
        response = restApiCalls.getResponseWithQueryParams(GET_SATELLITE_POSITION_BY_ID.replace("[id]",satellite_id),query_params);

        //storing the request params for response validation
        getSatelliteByIdRequest.setSatelliteId(satellite_id);
        getSatelliteByIdRequest.setUnits(query_params.get("units"));
        getSatelliteByIdRequest.setTimestamp(query_params.get("timestamps"));
    }

    @And("the error message should be {string}")
    public void theErrorShouldBe(String error) {
        if(!error.isEmpty()) {
            JsonPath jsonPathEvaluator = response.jsonPath();
            assertTrue(jsonPathEvaluator.get("error").toString().contains(error));
        }
    }

    @When("I get TLE data in {string} format for a given satellite {string}")
    public void iGetTLEDataInFormatForAGivenSatellite(String responseFormat, String satellite_id) {
       query_params.put("format",responseFormat);
       response = restApiCalls.getResponseWithQueryParams(GET_TLE_FOR_SATELLITE_BY_ID.replace("[id]", satellite_id), query_params);

        //storing the request params for response validation
        getTleBySatelliteIdRequest.setId(satellite_id);
        getTleBySatelliteIdRequest.setResponseFormat(responseFormat);
    }

    @And("the response body should have TLE data for given satellite")
    public void theResponseBodyShouldHaveTLEDataForGivenSatellite() throws JsonProcessingException {
        if(getTleBySatelliteIdRequest.getResponseFormat().equals("json"))
        {
            GetTleBySatelliteIdResponse getTleBySatelliteId=mapper.readValue(response.asString(), GetTleBySatelliteIdResponse.class);
        }
            assertTrue(response.asString().contains(getTleBySatelliteIdRequest.getId()));
    }

    @When("I get the satellite position for satellite id {string} with {int} timestamps")
    public void iGetTheSatellitePositionForSatelliteIdWithTimestamps(String satelliteId, int numberOfTimestamps) throws IOException {
        //read the timestamps from the timestamps file in data folder
        String data = new String(Files.readAllBytes(Paths.get("src/test/resources/data/timestamps")));
        List<String> timestamps = Arrays.asList(data.split(","));
        StringBuilder timestamps_header= new StringBuilder();

        //pick the specified number of timestamps from the timestamps file in data folder and pass it as a query params
        for(int i=0;i<numberOfTimestamps;i++)
        {
            timestamps_header.insert(0, timestamps.get(i) + ",");
        }
        timestamps_header = new StringBuilder(timestamps_header.substring(0, timestamps_header.length() - 1));
        query_params.put("timestamps", timestamps_header.toString());
        response = restApiCalls.getResponseWithQueryParams(GET_SATELLITE_POSITION_BY_ID.replace("[id]",satelliteId),query_params);

        getSatelliteByIdRequest.setSatelliteId(satelliteId);
        getSatelliteByIdRequest.setUnits(query_params.get("units"));
        getSatelliteByIdRequest.setTimestamp(query_params.get("timestamps"));
    }

    @Then("log the response when service is unavailable")
    public void logTheResponseWhenServiceIsUnavailable() {
        Log.warn("Service unreliable for "+serviceUnreliabilityCount+" time.");
    }

    @When("I get the satellite position for satellite {string} for below {int} times")
    public void iGetTheSatellitePositionForSatelliteForBelowTimes(String satellite_id, int load, DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        for (List<String> columns : rows) {
            query_params.put(columns.get(0), columns.get(1));
        }
        int i=0;
        //invoking the API n number of times
        while(i<load) {
            response = restApiCalls.getResponseWithQueryParams(GET_SATELLITE_POSITION_BY_ID.replace("[id]", satellite_id), query_params);

            //if response is not 200, log the service unreliability in the api.log file
            if(response.getStatusCode() != 200) {
                Log.warn("Service unavailable, status: " + response.statusCode());
                serviceUnreliabilityCount++;
            }
            i++;
        }
        Log.info("X-Rate-Limit-Remaining  "+response.header("X-Rate-Limit-Remaining"));
    }
}