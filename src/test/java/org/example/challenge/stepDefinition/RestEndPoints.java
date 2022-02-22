package org.example.challenge.stepDefinition;

import lombok.Data;

@Data
public class RestEndPoints {
    protected static final String BASE_URI = "https://api.wheretheiss.at/v1";
    protected static final String GET_SATELLITE_POSITION_BY_ID = BASE_URI + "/satellites/[id]/positions";
    protected static final String GET_TLE_FOR_SATELLITE_BY_ID = BASE_URI + "/satellites/[id]/tles";
}