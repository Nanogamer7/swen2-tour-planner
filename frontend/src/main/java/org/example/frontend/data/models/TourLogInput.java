package org.example.frontend.data.models;

import java.util.UUID;

public record TourLogInput(
        Long timestamp,
        String comment,
        Integer difficulty,
        Long distance,
        Integer duration,
        Integer rating) {
}
