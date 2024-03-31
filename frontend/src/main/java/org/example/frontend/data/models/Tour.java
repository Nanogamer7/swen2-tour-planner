package org.example.frontend.data.models;

import java.util.UUID;

public record Tour (
        UUID uuid,
        String name,
        String description,
        Coordinate from,
        Coordinate to,
        long distance, // in meters
        TransportType type,
        long estimated_time, // in seconds
        String mapFilename  // e.g. map392384.png
){

}
