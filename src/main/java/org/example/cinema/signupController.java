package org.example.cinema;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
import java.time.LocalDate;
import java.util.List;

public class signupController {
    private final ClientTypeController clientTypeController;
    private final UsersController usersController;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private TextField usernameField;
    @FXML
    private ComboBox<ClientType> clientTypeComboBox;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button registerButton;

    public signupController() {
        ClientTypeDBRepository clientTypeRepository = new ClientTypeDBRepository();
        ClientTypeService clientTypeService = new ClientTypeService(clientTypeRepository);
        this.clientTypeController = new ClientTypeController(clientTypeService);
        UsersDBRepository usersRepository = new UsersDBRepository();
        UsersService usersService = new UsersService(usersRepository, new UsersValidator(), new ClientValidator());
        this.usersController = new UsersController(usersService);
    }

    @FXML
    public void initialize() {
        loadClientTypes();
    }

    private void loadClientTypes() {
        List<ClientType> clientTypes = clientTypeController.handleGetClientTypes();
        clientTypeComboBox.setItems(FXCollections.observableArrayList(clientTypes));
        clientTypeComboBox.setCellFactory(listView ->
                new javafx.scene.control.ListCell<>() {
                    @Override
                    protected void updateItem(ClientType item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getTypeName());
                        }
                    }
                }
        );
        clientTypeComboBox.setButtonCell(
                new javafx.scene.control.ListCell<>() {
                    @Override
                    protected void updateItem(ClientType item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getTypeName());
                        }
                    }
                }
        );
    }

    @FXML
    public void onRegisterClick() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneField.getText();
        LocalDate dateOfBirth = dobPicker.getValue();
        String username = usernameField.getText();
        String password = passwordField.getText();
        ClientType selectedClientType = clientTypeComboBox.getSelectionModel().getSelectedItem();
        if (selectedClientType == null) {
            showMessage("Please select a client type.", "Registration failed");
            return;
        }
        try {
            usersController.handleAddUser(firstName, lastName, email, phoneNumber, dateOfBirth, username, password, selectedClientType.getTypeID());
            showMessage("Account created successfully.", "Registration successful");
            closeWindow();
        } catch (Exception e) {
            showMessage(e.getMessage() != null ? e.getMessage() : "Could not create account.", "Registration failed");
        }
    }

    private void showMessage(String message, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/messagebox-view.fxml"));
            Parent root = loader.load();
            messageboxController messageBoxController = loader.getController();
            messageBoxController.setMessage(message);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
    }
}