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
        long estimated_time // in seconds
){

    public String getOpenRouteServiceURL() {
        String transportType;
        if(this.type().equals(TransportType.WALK)) {
            transportType = "foot-hiking";
        } else {
            transportType = "cycling-regular";
        }

        return "https://api.openrouteservice.org/v2/directions/%s?start=%f,%f&end=%f,%f&api_key=5b3ce3597851110001cf6248e9d436a131fe4a0dbd27c51b86a581d7".formatted(
                transportType, this.from().latitude(), this.from().longitude(), this.to().latitude(), this.to().longitude()
        );
    }
}
