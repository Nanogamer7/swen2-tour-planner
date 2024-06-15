package org.example.frontend.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@RequiredArgsConstructor // for some reason data doesn't add this here
@AllArgsConstructor
public class TourLog {
    @JsonProperty("id")
    protected UUID uuid;

    protected Instant timestamp = Instant.ofEpochSecond(0);
    protected String comment = "";
    protected Integer difficulty = 1;
    protected Long distance = 0L;
    protected Long duration = 0L;
    protected Integer rating = 1;

    protected Boolean outdated;


}