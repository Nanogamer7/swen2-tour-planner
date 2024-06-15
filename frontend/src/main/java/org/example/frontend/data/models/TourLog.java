package org.example.frontend.data.models;

import java.util.UUID;

public record TourLog(
        UUID uuid,
        long timestamp, // unix epoch
        String comment,
        int difficulty,
        int distance,
        long duration,
        int rating,
        Boolean outdated) {
}