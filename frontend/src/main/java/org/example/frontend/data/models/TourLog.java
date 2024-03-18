package org.example.frontend.data.models;

public class TourLog{
    protected String uuid;

    protected Long timestamp;
    protected String comment;
    protected Integer difficulty;
    protected Long distance;
    protected Integer duration;
    protected Integer rating;

    protected Boolean outdated;


    public TourLog(String uuid, long timestamp, String comment, int difficulty, long distance, int duration, int rating, boolean outdated) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.comment = comment;
        this.difficulty = difficulty;
        this.distance = distance;
        this.duration = duration;
        this.rating = rating;
        this.outdated = outdated;
    }


    public String getUuid() {
        return uuid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getComment() {
        return comment;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public Long getDistance() {
        return distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getRating() {
        return rating;
    }

    public Boolean getOutdated() {
        return outdated;
    }
}
