package org.example.frontend.data.models;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransportType {
    WALK("Walk"),
    BIKE("Bike"),
    POGO_STICK("Pogo stick");

    public final String name;
}
