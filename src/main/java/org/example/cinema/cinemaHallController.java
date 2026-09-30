package org.example.cinema;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.cinema.controller.CinemaHallController;
import org.example.cinema.controller.PricingController;
import org.example.cinema.controller.ReservationsController;
import org.example.cinema.controller.ScreeningController;
import org.example.cinema.controller.UsersController;
import org.example.cinema.domain.CinemaHall;
import org.example.cinema.domain.Client;
import org.example.cinema.domain.Pricing;
import org.example.cinema.domain.Reservations;
import org.example.cinema.domain.Screening;
import org.example.cinema.domain.Users;
import org.example.cinema.repository.db.CinemaHallDBRepository;
import org.example.cinema.repository.db.PricingDBRepository;
import org.example.cinema.repository.db.ReservationDBRepository;
import org.example.cinema.repository.db.ScreeningDBRepository;
import org.example.cinema.repository.db.UsersDBRepository;
import org.example.cinema.service.CinemaHallService;
import org.example.cinema.service.PricingService;
import org.example.cinema.service.ReservationsService;
import org.example.cinema.service.ScreeningService;
import org.example.cinema.service.UsersService;
import org.example.cinema.validators.ClientValidator;
import org.example.cinema.validators.ScreeningValidator;
import org.example.cinema.validators.UsersValidator;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class cinemaHallController {
    private int userID;
    private int screeningID;
    private int pricingID = -1;
    private int row = -1;
    private int col = -1;
    private final Set<String> selectedSeats = new HashSet<>();
    private final CinemaHallController cinemaHallController;
    private final ScreeningController screeningController;
    private final ReservationsController reservationsController;
    private final PricingController pricingController;
    private final UsersController usersController;
    @FXML
    private GridPane seatsGrid;
    @FXML
    private Label selectedSeatLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Button reserveButton;
    @FXML
    private Button editButton;
    @FXML
    private TextField reservationIDField;

    public cinemaHallController() {
        CinemaHallService cinemaHallService = new CinemaHallService(new CinemaHallDBRepository());
        this.cinemaHallController = new CinemaHallController(cinemaHallService);
        ScreeningService screeningService = new ScreeningService(new ScreeningDBRepository(), new ScreeningValidator());
        this.screeningController = new ScreeningController(screeningService);
        ReservationsService reservationsService = new ReservationsService(new ReservationDBRepository());
        this.reservationsController = new ReservationsController(reservationsService);
        PricingService pricingService = new PricingService(new PricingDBRepository());
        this.pricingController = new PricingController(pricingService);
        UsersService usersService = new UsersService(new UsersDBRepository(), new UsersValidator(), new ClientValidator());
        this.usersController = new UsersController(usersService);
    }

    public void setData(int userID, int screeningID) {
        this.userID = userID;
        this.screeningID = screeningID;
        loadHall();
    }

    private void loadHall() {
        Screening screening = screeningController.handleGetScreening(screeningID);
        if (screening == null) {
            showMessage("Screening not found.", "Error");
            return;
        }
        CinemaHall cinemaHall = cinemaHallController.handleGetCinemaHall(screening.getCinemaHallID());
        if (cinemaHall == null) {
            showMessage("Cinema hall not found.", "Error");
            return;
        }
        List<Reservations> reservations = reservationsController.handleGetReservationsByScreening(screeningID);
        loadSeats(cinemaHall, reservations);
        loadPricing(screening);
    }

    private void loadSeats(CinemaHall cinemaHall, List<Reservations> reservations) {
        int rows = cinemaHall.getRows();
        int columns = cinemaHall.getColumns();
        seatsGrid.getChildren().clear();
        selectedSeats.clear();
        row = -1;
        col = -1;
        selectedSeatLabel.setText("");
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= columns; c++) {
                Button seatButton = new Button(r + "-" + c);
                seatButton.setPrefSize(30, 30);
                seatButton.setMinSize(30, 30);
                seatButton.setMaxSize(30, 30);
                if (isReserved(reservations, r, c)) {
                    seatButton.setDisable(true);
                    seatButton.setStyle("-fx-font-size: 6px; " + "-fx-background-color: #7f8c8d; " + "-fx-text-fill: white;");
                } else {
                    seatButton.setStyle("-fx-font-size: 6px; " + "-fx-background-color: #f9e79f; " + "-fx-text-fill: black;");
                    final int seatRow = r;
                    final int seatColumn = c;
                    seatButton.setOnAction(event -> toggleSeatSelection(seatButton, seatRow, seatColumn));
                }
                seatsGrid.add(seatButton, c - 1, r - 1);
            }
        }
    }

    private void loadPricing(Screening screening) {
        pricingID = -1;
        reserveButton.setDisable(true);
        editButton.setDisable(true);
        Users user = usersController.handleGetUser(userID);
        if (!(user instanceof Client client)) {
            showMessage("Client not found.", "Error");
            return;
        }
        Pricing pricing = pricingController.handleGetPricing(client.getTypeID(), screening.getTypeID());
        if (pricing == null) {
            priceLabel.setText("Unavailable");
            showMessage("Pricing not found.", "Error");
            return;
        }
        pricingID = pricing.getPriceID();
        priceLabel.setText(pricing.getPrice() + " RON");
        reserveButton.setDisable(false);
        editButton.setDisable(false);
    }

    private void toggleSeatSelection(Button seatButton, int row, int col) {
        String key = row + "-" + col;
        if (selectedSeats.contains(key)) {
            selectedSeats.clear();
            this.row = -1;
            this.col = -1;
            selectedSeatLabel.setText("");
            seatButton.setStyle("-fx-font-size: 6px; " + "-fx-background-color: #f9e79f; " + "-fx-text-fill: black;");
            return;
        }
        resetAvailableSeatStyles();
        selectedSeats.clear();
        selectedSeats.add(key);
        this.row = row;
        this.col = col;
        selectedSeatLabel.setText(key);
        seatButton.setStyle("-fx-font-size: 6px; " + "-fx-background-color: #27ae60; " + "-fx-text-fill: white;");
    }

    private void resetAvailableSeatStyles() {
        seatsGrid.getChildren().forEach(node -> {
            if (node instanceof Button button && !button.isDisabled()) {
                button.setStyle("-fx-font-size: 6px; " + "-fx-background-color: #f9e79f; " + "-fx-text-fill: black;");
            }
        });
    }

    private boolean isReserved(List<Reservations> reservations, int row, int col) {
        for (Reservations reservation : reservations) {
            if (reservation.getRowReservation() == row && reservation.getColumnReservation() == col) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void handleReservation() {
        if (row == -1 || col == -1) {
            showMessage("Please select your seat.", "Error");
            return;
        }
        int generatedID = reservationsController.handleAddReservation(userID, screeningID, pricingID, row, col);
        if (generatedID == -1) {
            showMessage("Failed reservation.", "Error");
            return;
        }
        loadHall();
        showMessage("Reservation " + generatedID + " has been made. Please remember the ID.", "Successful reservation");
    }

    @FXML
    private void handleUpdate() {
        if (reservationIDField.getText().isBlank()) {
            showMessage("Please enter your reservation ID.", "Error");
            return;
        }
        if (row == -1 || col == -1) {
            showMessage("Please select your new seat.", "Error");
            return;
        }
        try {
            int reservationID = Integer.parseInt(reservationIDField.getText());
            Reservations reservation = reservationsController.handleGetReservation(reservationID);
            if (reservation == null) {
                showMessage("Reservation not found.", "Error");
                return;
            }
            if (reservation.getClientID() != userID) {
                showMessage("This reservation does not belong to you.", "Error");
                return;
            }
            if (reservation.getScreeningID() != screeningID) {
                showMessage("This reservation belongs to another screening.", "Error");
                return;
            }
            int newRow = row;
            int newCol = col;
            reservationsController.handleUpdateReservation(reservationID, userID, screeningID, pricingID, newRow, newCol);
            loadHall();
            reservationIDField.clear();
            showMessage("Your new seat is " + newRow + "-" + newCol + ".", "Reservation " + reservationID + " has been updated.");
        } catch (NumberFormatException e) {
            showMessage("Reservation ID must be a valid number.", "Error");
        }
    }

    private void showMessage(String message, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource( "/org/example/cinema/messagebox-view.fxml"));
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
}