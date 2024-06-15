package org.example.frontend.data.models;

import java.util.UUID;

public record TourInput(
        String name,
        String description,
        Double from_lat,
        Double from_long,
        Double to_lat,
        Double to_long,
        TransportType type
) {
}
