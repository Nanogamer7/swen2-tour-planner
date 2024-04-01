package org.example.frontend.data.models;

import java.util.UUID;

public record TourInput(
        String name,
        String description,
        Coordinate from,
        Coordinate to,
        TransportType type
) {
}
