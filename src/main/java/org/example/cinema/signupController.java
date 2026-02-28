package org.example.cinema;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.cinema.controller.ClientTypeController;
import org.example.cinema.controller.UsersController;
import org.example.cinema.domain.ClientType;
import org.example.cinema.repository.db.ClientTypeDBRepository;
import org.example.cinema.repository.db.UsersDBRepository;
import org.example.cinema.service.ClientTypeService;
import org.example.cinema.service.UsersService;
import org.example.cinema.validators.ClientValidator;
import org.example.cinema.validators.UsersValidator;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class signupController {
    private ClientTypeDBRepository clientTypeRepository;
    private ClientTypeService clientTypeService;
    private ClientTypeController clientTypeController;
    private UsersDBRepository usersRepository;
    private UsersService usersService;
    private UsersController usersController;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private DatePicker dobPicker;
    @FXML private TextField usernameField;
    @FXML private ComboBox<String> clientTypeComboBox;
    @FXML private PasswordField passwordField;
    @FXML private Button registerButton;

    public void initialize() {
        loadClientTypes();
    }

    private void loadClientTypes() {
        clientTypeRepository = new ClientTypeDBRepository();
        clientTypeService = new ClientTypeService(clientTypeRepository);
        clientTypeController = new ClientTypeController(clientTypeService);
        List<ClientType> clientTypes = clientTypeController.handleGetClientTypes();
        List<String> typeNames = new ArrayList<>();
        for (ClientType clientType : clientTypes) {
            typeNames.add(clientType.getTypeName());
        }
        ObservableList<String> observableTypes = FXCollections.observableArrayList(typeNames);
        clientTypeComboBox.setItems(observableTypes);
    }

    @FXML
    public void onRegisterClick() throws SQLException, IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        LocalDate dob = dobPicker.getValue();
        String username = usernameField.getText();
        String password = passwordField.getText();
        int typeID = clientTypeComboBox.getSelectionModel().getSelectedIndex() + 1;
        usersRepository = new UsersDBRepository();
        usersService = new UsersService(usersRepository, new UsersValidator(), new ClientValidator());
        usersController = new UsersController(usersService);
        try {
            usersController.handleAddUser(firstName, lastName, email, phone, dob, username, password, typeID);
            generateMessagebox("Successful inregistration", "account added");
            Stage stage = (Stage)registerButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            generateMessagebox("Failed inregistration", "error");
        }
    }

    private void generateMessagebox(String s1, String s2) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/messagebox-view.fxml"));
        Parent root = loader.load();
        messageboxController messageBoxController = loader.getController();
        messageBoxController.setMessage(s1);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(s2);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }
}
