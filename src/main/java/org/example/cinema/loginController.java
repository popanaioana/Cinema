package org.example.cinema;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.time.Period;

public class loginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private final UsersController usersController;

    public loginController() {
        UsersDBRepository usersRepository = new UsersDBRepository();
        UsersService usersService = new UsersService(usersRepository, new UsersValidator(), new ClientValidator());
        this.usersController = new UsersController(usersService);
    }

    @FXML
    public void onLoginClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            showError("Username and password are required.");
            return;
        }
        Users user = usersController.handleGetUser(username, password);
        if (user == null) {
            showError("Wrong username or password.");
            return;
        }
        if (user instanceof Client) {
            openClientProgram(user);
        } else if (user instanceof AdminCinema) {
            openAdminPanel();
        }
    }

    private void openClientProgram(Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/cinema/program-view.fxml")
            );
            Parent root = loader.load();
            programController programController = loader.getController();
            programController.setLoggedUser(user.getUserID());
            int age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
            programController.setUserAge(age);
            openStage(root, "Cinema program");
            closeLoginWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAdminPanel() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/cinema/admin-view.fxml")
            );
            Parent root = loader.load();
            openStage(root, "Administration");
            closeLoginWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/cinema/messagebox-view.fxml")
            );
            Parent root = loader.load();
            messageboxController messageBoxController = loader.getController();
            messageBoxController.setMessage(message);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Error");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openStage(Parent root, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void closeLoginWindow() {
        Stage currentStage = (Stage) usernameField.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void onSignUpClick() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/cinema/signup-view.fxml")
            );
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Sign Up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}