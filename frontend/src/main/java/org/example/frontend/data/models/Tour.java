package org.example.frontend.data.models;

import org.example.frontend.base.Coordinate;
import org.example.frontend.base.TransportType;

public record Tour (
    int id,

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
