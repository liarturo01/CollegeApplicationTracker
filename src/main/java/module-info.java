module finalproject.collegeapplicationtracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens finalproject.collegeapplicationtracker to javafx.fxml;
    exports finalproject.collegeapplicationtracker;
}