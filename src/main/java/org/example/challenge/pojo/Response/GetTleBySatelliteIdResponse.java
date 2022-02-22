package org.example.challenge.pojo.Response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class GetTleBySatelliteIdResponse {
    private BigDecimal requested_timestamp;
    private BigDecimal tle_timestamp;
    private String id;
    private String name;
    private String header;
    private String line1;
    private String line2;
}