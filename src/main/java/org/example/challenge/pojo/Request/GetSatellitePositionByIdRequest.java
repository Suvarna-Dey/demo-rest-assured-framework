package org.example.challenge.pojo.Request;

import lombok.Data;

@Data
public class GetSatellitePositionByIdRequest {
    private String satelliteId;
    private String timestamp;
    private String units;
}
