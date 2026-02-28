package org.example.cinema;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.cinema.controller.MovieController;
import org.example.cinema.repository.db.MovieDBRepository;
import org.example.cinema.service.MovieService;
import org.example.cinema.validators.MovieValidator;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/cinema/login-view.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Cinema Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
