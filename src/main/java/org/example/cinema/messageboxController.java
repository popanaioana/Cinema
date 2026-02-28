package org.example.cinema;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class messageboxController {

    @FXML
    private Label messageLabel;

    @FXML
    private Button okButton;

    @FXML
    private void onOkClicked() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }
}
