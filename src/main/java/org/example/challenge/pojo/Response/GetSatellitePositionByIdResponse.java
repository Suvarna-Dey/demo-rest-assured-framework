package org.example.challenge.pojo.Response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class GetSatellitePositionByIdResponse {
    private String name;
    private int id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal altitude;
    private BigDecimal velocity;
    private String visibility;
    private BigDecimal footprint;
    private String timestamp;
    private BigDecimal daynum;
    private BigDecimal solar_lat;
    private BigDecimal solar_lon;
    private String units;
}