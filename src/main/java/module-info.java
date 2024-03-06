module tn.esprit.football.football {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires android.json;
    requires poi;
    requires poi.ooxml;
    requires itextpdf;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens tn.esprit.football to javafx.fxml;
    exports tn.esprit.football;
    exports controllers;
    opens controllers;
    exports Controller;
    opens Controller;
}