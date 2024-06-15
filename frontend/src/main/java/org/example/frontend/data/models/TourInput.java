package org.example.frontend.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record TourInput(
        String name,
        String description,
        @JsonProperty("start_lat")
        Double from_lat,
        @JsonProperty("start_long")
        Double from_long,
        @JsonProperty("end_lat")
        Double to_lat,
        @JsonProperty("end_long")
        Double to_long,
        TransportType type
) {
}
