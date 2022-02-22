package org.example.challenge.pojo.Request;

import lombok.Data;

@Data
public class GetTleBySatelliteIdRequest {
    private String id;
    private String responseFormat;
}
