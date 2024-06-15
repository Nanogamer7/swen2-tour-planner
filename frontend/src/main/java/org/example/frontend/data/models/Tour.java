package org.example.frontend.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@RequiredArgsConstructor // for some reason data doesn't add this here
@AllArgsConstructor
public class Tour {
       @JsonProperty("id")
       private UUID uuid;
       private String name;
       private String description;
       @JsonProperty("start_lat")
       private double from_lat;
       @JsonProperty("start_long")
       private double from_long;
       @JsonProperty("end_lat")
       private double to_lat;
       @JsonProperty("end_long")
       private double to_long;
       private long distance; // in meters
       private TransportType type;
       @JsonProperty("time")
       private long estimated_time; // in seconds
}
