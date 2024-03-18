package org.example.frontend.data.models;

public record Tour (
    String uuid,

    String name,
    String description,
    Coordinate from,
    Coordinate to,
    long distance, // in meters
    TransportType type,
    long estimated_time, // in seconds
    String map_filename  // e.g. map392384.png
){

}
