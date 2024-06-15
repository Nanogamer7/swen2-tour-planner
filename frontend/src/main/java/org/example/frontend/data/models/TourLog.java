package org.example.frontend.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@RequiredArgsConstructor // for some reason data doesn't add this here
@AllArgsConstructor
public class TourLog {
    @JsonProperty("id")
    protected String uuid;

    protected Long timestamp;
    protected String comment;
    protected Integer difficulty;
    protected Long distance;
    @JsonProperty("time")
    protected Integer duration;
    protected Integer rating;

    protected Boolean outdated;


}
