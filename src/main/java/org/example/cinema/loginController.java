package org.example.cinema;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.cinema.controller.UsersController;
import org.example.cinema.domain.AdminCinema;
import org.example.cinema.domain.Client;
import org.example.cinema.domain.Users;
import org.example.cinema.repository.db.UsersDBRepository;
import org.example.cinema.service.UsersService;
import org.example.cinema.validators.ClientValidator;
import org.example.cinema.validators.UsersValidator;

import java.io.IOException;
import java.time.LocalDate;

public class loginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private UsersDBRepository usersRepository;
    private UsersService usersService;
    private UsersController usersController;

    @FXML
    public void onLoginClick() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        usersRepository = new UsersDBRepository();
        usersService = new UsersService(usersRepository, new UsersValidator(), new ClientValidator());
        usersController = new UsersController(usersService);
        Users userDB = usersController.handleGetUser(user, pass);
        if (userDB == null){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/messagebox-view.fxml"));
                Parent root = loader.load();
                messageboxController messageBoxController = loader.getController();
                messageBoxController.setMessage("wrong username or password.");
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Error");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (userDB instanceof Client) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/org/example/cinema/program-view.fxml")
                    );
                    Parent root = loader.load();
                    programController programCtrl = loader.getController();
                    programCtrl.setLoggedUser(userDB.getUserID());
                    programCtrl.setUserAge(LocalDate.now().getYear() - userDB.getDateOfBirth().getYear());
                    Stage stage = new Stage();
                    stage.setTitle("Cinema program");
                    stage.setScene(new Scene(root));
                    stage.show();
                    Stage currentStage = (Stage) usernameField.getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (userDB instanceof AdminCinema) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/org/example/cinema/admin-view.fxml")
                    );
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Administration");
                    stage.setScene(new Scene(root));
                    stage.show();
                    Stage currentStage = (Stage) usernameField.getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void onSignUpClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/signup-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Sign Up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
