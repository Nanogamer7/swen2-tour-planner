package org.example.frontend.components.TourLogs;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.frontend.base.FormVisibilityListener;
import org.example.frontend.data.models.TourLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TourLogsViewModel implements FormVisibilityListener {
    public ObservableList<FormattedTourLog> formattedTourLogs;
    public BooleanProperty formVisible = new SimpleBooleanProperty(false);



    public static class FormattedTourLog extends TourLog {
        public FormattedTourLog(TourLog log) {
            super(log.getUuid(), log.getTimestamp(), log.getComment(), log.getDifficulty(), log.getDistance(), log.getDuration(), log.getRating(), log.getOutdated());
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

    public void onFormVisible(boolean visible) {
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
