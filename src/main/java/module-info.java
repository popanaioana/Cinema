module org.example.cinema {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;
    //requires org.example.cinema;

    opens org.example.cinema to javafx.fxml;
    opens org.example.cinema.domain to javafx.base;
    opens org.example.cinema.controller to javafx.fxml;
    exports org.example.cinema;
}