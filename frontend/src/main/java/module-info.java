module org.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires java.desktop;
    requires java.net.http;
    requires jdk.httpserver;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.json;

    opens org.example.frontend to javafx.fxml;
    exports org.example.frontend;
    exports org.example.frontend.components.TourMenu;
    exports org.example.frontend.components.TourDetails;
    exports org.example.frontend.components.TourLogs;
    exports org.example.frontend.components.TourList;
    exports org.example.frontend.components.TourForm;
    exports org.example.frontend.data.models;
    exports org.example.frontend.base;
    opens org.example.frontend.components.TourMenu to javafx.fxml;
    exports org.example.frontend.components.TourLogsForm;
}