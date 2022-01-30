package org.truelayer.challenge.stepDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.truelayer.challenge.apiSupport.RestApiCalls;
import org.truelayer.challenge.pojo.Request.GetSatellitePositionByIdRequest;
import org.truelayer.challenge.pojo.Request.GetTleBySatelliteIdRequest;
import org.truelayer.challenge.pojo.Response.GetSatellitePositionByIdResponse;
import org.truelayer.challenge.pojo.Response.GetTleBySatelliteIdResponse;

import java.util.*;

import static org.junit.Assert.*;

public class WhereTheIssApi extends RestEndPoints{
    private final RestApiCalls restApiCalls = new RestApiCalls();
    private Response response;
    private ObjectMapper mapper = new ObjectMapper();
    private Map<String,String> query_params = new HashMap<>();
    private GetSatellitePositionByIdRequest getSatelliteByIdRequest = new GetSatellitePositionByIdRequest();
    private GetTleBySatelliteIdRequest getTleBySatelliteIdRequest =new GetTleBySatelliteIdRequest();

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int responseCode) {
        assertEquals("Response code mismatch, errorMessage: "+response.getBody().asString(), responseCode, response.getStatusCode());
    }

    @Then("the response body should be have satellite position")
    public void the_response_body_should_be_have_satellite_position() throws JsonProcessingException {
        GetSatellitePositionByIdResponse[] getSatellitePositionResponse =  mapper.readValue(response.asString(), GetSatellitePositionByIdResponse[].class);

        //Check if total positions returned are equal to the timestamps
        List<String> timestamps = Arrays.asList(query_params.get("timestamps").split(","));
        assertEquals(timestamps.size(),getSatellitePositionResponse.length);

        //response validation
        for(GetSatellitePositionByIdResponse resultJson:getSatellitePositionResponse)
        {
            assertNotNull(resultJson.getName());
            assertEquals(getSatelliteByIdRequest.getSatelliteId(),String.valueOf(resultJson.getId()));
            assertNotNull(resultJson.getLatitude());
            assertNotNull(resultJson.getLongitude());
            assertNotNull(resultJson.getAltitude());
            assertTrue(timestamps.contains(resultJson.getTimestamp()));
            assertNotNull(resultJson.getSolar_lat());
            assertNotNull(resultJson.getSolar_lon());
            if (getSatelliteByIdRequest.getUnits()!=null)
                assertEquals(getSatelliteByIdRequest.getUnits(), resultJson.getUnits());
            else
                assertEquals("kilometers", resultJson.getUnits());
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
    public void iGetTheSatellitePositionForSatelliteIdWithTimestamps(String satelliteId, int numberOfTimestamps) {

    }
}