package org.example.frontend.data.models;

public record TourLogInput(
        Long timestamp,
        String comment,
        Integer difficulty,
        Long distance,
        Long duration,
        Integer rating) {
}
