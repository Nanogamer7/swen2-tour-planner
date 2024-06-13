package at.fhtw.tourplanner.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tour_logs")
@Data
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourLog {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_id", nullable = false)
    @JsonIgnore
    private Tour tour;
    @Column(name = "\"timestamp\"", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant timestamp;
    private Integer difficulty;
    private Integer distance;
    @Column(name = "\"time\"")
    private Integer time;
    private Integer rating;
    private Boolean outdated;

}