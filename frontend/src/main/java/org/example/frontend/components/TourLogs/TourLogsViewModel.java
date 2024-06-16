package org.example.frontend.components.TourLogs;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.example.frontend.base.LogUpdateListener;
import org.example.frontend.base.TourLogFormVisibilityListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.TourLog;

import java.util.*;

public class TourLogsViewModel implements TourLogFormVisibilityListener, LogUpdateListener {
    public ObservableList<FormattedTourLog> formattedTourLogs;
    public BooleanProperty formVisible = new SimpleBooleanProperty(false);

    private UUID selectedLogId = null;

    @Override
    public void updateLog(TourLog log) {
        this.selectedLogId = Optional.ofNullable(log).map(TourLog::getUuid).orElse(null);
    }

    @Getter
    public static class FormattedTourLog extends TourLog {
        public FormattedTourLog(TourLog log) {
            super(
                    log.getUuid(),
                    log.getTimestamp(),
                    log.getComment(),
                    log.getDifficulty(),
                    log.getDistance(),
                    log.getDuration(),
                    log.getRating(),
                    log.getOutdated()
            );
        }

        public String getDate() {
            var dateFormat = new java.text.SimpleDateFormat("dd.MM.yy HH:mm");
            return dateFormat.format(Date.from(timestamp));  // *1000 => convert seconds to ms
        }

        public String getFormattedDuration() {
            if (this.duration == null || this.duration == 0) {
                return "--:--";
            }
            var hours = Math.floorDiv(duration, 60*60);
            var minutes = Math.floorDiv(duration, 60) % 60;
            var seconds = duration % 60;

            return hours > 0 ?
                    String.format("%02d:%02d:%02d", hours, minutes, seconds) :
                    String.format("%02d:%02d", minutes, seconds);
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

    public void deleteTourLog() {
        if (selectedLogId == null) {
            return;
        }

        TourRepository.getInstance().deleteLog(this.selectedLogId);
    }
}
