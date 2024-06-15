package org.example.frontend.components.TourLogs;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.example.frontend.base.TourFormVisibilityListener;
import org.example.frontend.base.TourLogFormVisibilityListener;
import org.example.frontend.data.models.TourLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TourLogsViewModel implements TourLogFormVisibilityListener {
    public ObservableList<FormattedTourLog> formattedTourLogs;
    public BooleanProperty formVisible = new SimpleBooleanProperty(false);


    @Getter
    public static class FormattedTourLog {
        public final UUID uuid;
        public final long timestamp;
        private final String comment;
        private final int difficulty;
        private final long distance;
        private final long duration;
        private final int rating;
        private final boolean outdated;

        public FormattedTourLog(TourLog log) {
            uuid = log.getUuid();
            timestamp = log.getTimestamp().getEpochSecond();
            comment = log.getComment();
            difficulty = log.getDifficulty();
            distance = log.getDistance();
            duration = log.getDuration();
            rating = log.getRating();
            outdated = log.getOutdated();
        }

        public String getDate() {
            var dateFormat = new java.text.SimpleDateFormat("dd.MM.yy HH:mm");
            return dateFormat.format(new Date(timestamp*1000));  // *1000 => convert seconds to ms
        }

        public String getFormattedDuration() {
            var hours = Math.floorDiv(duration, 60*60);
            var minutes = Math.floorDiv(duration, 60) % 60;

            return String.format("%02d:%02d", hours, minutes);
        }
    }

    public void onLogsFormVisible(boolean visible) {
        this.formVisible.set(visible);
    }


    public void updateTourLogs(List<TourLog> tourLogs) {
        var formattedTourLogs = new ArrayList<FormattedTourLog>();

        for(var log: tourLogs){
            formattedTourLogs.add(new FormattedTourLog(log));
        }

        this.formattedTourLogs = FXCollections.observableArrayList(formattedTourLogs);
    }
}
