package at.fhtw.tourplanner.models;

import at.fhtw.tourplanner.TransportType;
import at.fhtw.tourplanner.data.ors.DirectionService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.UUID;

@Entity
@Table(name = "tours")
@Data
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class Tour {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private double start_lat;
    private double start_long;
    private double end_lat;
    private double end_long;

    private TransportType type;
    @Transient
    @JsonInclude
    private Integer distance = null;
    @Transient
    @JsonInclude
    private Integer time = null;
}
