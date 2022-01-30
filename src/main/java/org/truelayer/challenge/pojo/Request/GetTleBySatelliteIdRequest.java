package org.truelayer.challenge.pojo.Request;

import lombok.Data;

@Data
public class GetTleBySatelliteIdRequest {
    private String id;
    private String responseFormat;
}
