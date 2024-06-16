package org.example.frontend.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TourLogInput(
        String comment,
        Integer difficulty,
        Long distance,
        @JsonProperty("time")
        Long duration,
        Integer rating) {
}
