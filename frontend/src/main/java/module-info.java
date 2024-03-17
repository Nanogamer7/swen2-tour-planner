module org.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.frontend to javafx.fxml;
    exports org.example.frontend;
    exports org.example.frontend.components.TourMenu;
    opens org.example.frontend.components.TourMenu to javafx.fxml;
}