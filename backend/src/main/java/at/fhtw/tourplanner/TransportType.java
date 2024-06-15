package at.fhtw.tourplanner;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransportType {
    CAR("driving-car", "Car"),
    CAR_HEAVY("driving-hgv", "Heavy Vehicle"),
    BIKE("cycling-regular", "Bike"),
    BIKE_ROAD("cycling-road", "Road Bike"),
    BIKE_MOUNTAIN("cycling-mountain", "Mountain Bike"),
    BIKE_ELECTRIC("cycling-electric", "E-Bike"),
    FOOT_WALK("foot-walking", "Foot Walking"),
    FOOT_HIKE("foot-hiking", "Foot Hiking"),
    WHEELCHAIR("wheelchair", "Wheelchair");

    // API string for the openrouteservice api
    public final String apiString;
    public final String description;

    @JsonValue
    public String getApiString() {
        return apiString;
    }

    public static TransportType fromApiString(String apiString) {
        for (TransportType type : TransportType.values()) {
            if (type.apiString.equals(apiString)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with apiString " + apiString);
    }
}
