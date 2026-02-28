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
import org.example.cinema.controller.*;
import org.example.cinema.domain.*;
import org.example.cinema.repository.db.*;
import org.example.cinema.service.*;
import org.example.cinema.validators.ClientValidator;
import org.example.cinema.validators.UsersValidator;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class cinemaHallController {
    private int userID;
    private int screeningID;
    private int pricingID;
    private int reservationID;
    private int row = -1;
    private int col = -1;
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
    private final Set<String> selectedSeats = new HashSet<>();
    private CinemaHallDBRepository cinemaHallRepository;
    private CinemaHallService cinemaHallService;
    private CinemaHallController cinemaHallController;
    private ScreeningDBRepository screeningRepository;
    private ScreeningService screeningService;
    private ScreeningController screeningController;
    private ReservationDBRepository reservationRepository;
    private ReservationsService reservationsService;
    private ReservationsController reservationsController;
    private PricingDBRepository pricingRepository;
    private PricingService pricingService;
    private PricingController pricingController;
    private UsersDBRepository usersRepository;
    private UsersService usersService;
    private UsersController usersController;

    public void setData(int userID, int screeningID) {
        this.userID = userID;
        this.screeningID = screeningID;
        loadHall();
    }

    private void loadHall() {
        screeningRepository = new ScreeningDBRepository();
        screeningService = new ScreeningService(screeningRepository);
        screeningController = new ScreeningController(screeningService);
        reservationRepository = new ReservationDBRepository();
        reservationsService = new ReservationsService(reservationRepository);
        pricingRepository = new PricingDBRepository();
        pricingService = new PricingService(pricingRepository);
        pricingController = new PricingController(pricingService);
        reservationsController = new ReservationsController(reservationsService, pricingService);
        cinemaHallRepository = new CinemaHallDBRepository();
        cinemaHallService = new CinemaHallService(cinemaHallRepository);
        cinemaHallController = new CinemaHallController(cinemaHallService);
        Screening screening = screeningController.handleGetScreening(screeningID);
        CinemaHall cinemaHall = cinemaHallController.handleGetCinemaHall(screening.getCinemaHallID());
        int rows = cinemaHall.getRows();
        int cols = cinemaHall.getColumns();
        List<Reservations> reservations = reservationsController.handleGetReservationsByScreening(screeningID);
        seatsGrid.getChildren().clear();
        selectedSeats.clear();
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                Button seatBtn = new Button(r + "-" + c);
                seatBtn.setPrefSize(30, 30);
                seatBtn.setMinSize(30, 30);
                seatBtn.setMaxSize(30, 30);
                boolean reserved = isReserved(reservations, r, c);
                if (reserved) {
                    seatBtn.setDisable(true);
                    seatBtn.setStyle("-fx-font-size: 6px; -fx-background-color: #7f8c8d; -fx-text-fill: white;");
                } else {
                    seatBtn.setStyle("-fx-font-size: 6px; -fx-background-color: #f9e79f; -fx-text-fill: black;");
                    final int row = r;
                    final int col = c;
                    seatBtn.setOnAction(e -> toggleSeatSelection(seatBtn, row, col));
                }
                seatsGrid.add(seatBtn, c - 1, r - 1);
            }
        }
        usersRepository = new UsersDBRepository();
        usersService = new UsersService(usersRepository, new UsersValidator(), new ClientValidator());
        usersController = new UsersController(usersService);
        Users client = usersController.handleGetUser(userID);
        int userTypeID = -1;
        if (client instanceof Client) {
            userTypeID = ((Client) client).getTypeID();
        }
        screeningRepository = new ScreeningDBRepository();
        screeningService = new ScreeningService(screeningRepository);
        screeningController = new ScreeningController(screeningService);
        Screening screening1 = screeningController.handleGetScreening(screeningID);
        int screeningTypeID = screening1.getTypeID();
        pricingRepository = new PricingDBRepository();
        pricingService = new PricingService(pricingRepository);
        pricingController = new PricingController(pricingService);
        Pricing pricing = pricingController.handleGetPricing(userTypeID, screeningTypeID);
        this.pricingID = pricing.getPriceID();
        priceLabel.setText(String.valueOf(pricing.getPrice()) + " RON");
        reservationRepository = new ReservationDBRepository();
        reservationsService = new ReservationsService(reservationRepository);
        reservationsController = new ReservationsController(reservationsService, pricingService);
    }

    private void toggleSeatSelection(Button seatBtn, int row, int col) {
        this.row = row;
        this.col = col;
        String key = row + "-" + col;
        if (!selectedSeats.contains(key)) {
            seatsGrid.getChildren().forEach(node -> {
                if (node instanceof Button btn) {
                    String btnKey = btn.getText();
                    if (!isReserved(btnKey)) {
                        btn.setStyle("-fx-font-size: 6px; -fx-background-color: #f9e79f; -fx-text-fill: black;");
                        selectedSeatLabel.setText(key);
                    }
                }
            });
            selectedSeats.clear();
            selectedSeats.add(key);
            seatBtn.setStyle("-fx-font-size: 6px; -fx-background-color: #27ae60; -fx-text-fill: white;");
        } else {
            selectedSeats.remove(key);
            seatBtn.setStyle("-fx-font-size: 6px; -fx-background-color: #f9e79f; -fx-text-fill: black;");
        }
    }

    private boolean isReserved(String buttonText) {
        String[] parts = buttonText.split("-");
        int r = Integer.parseInt(parts[0]);
        int c = Integer.parseInt(parts[1]);
        List<Reservations> reservations = reservationsController.handleGetReservationsByScreening(screeningID);
        return isReserved(reservations, r, c);
    }

    private boolean isReserved(List<Reservations> reservations, int row, int col) {
        for (Reservations r : reservations) {
            if (r.getRow() == row && r.getCol() == col) {
                return true;
            }
        } return false;
    }

    @FXML
    private void handleReservation() {
        if (row == -1 || col == -1) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/messagebox-view.fxml"));
                Parent root = loader.load();
                messageboxController messageBoxController = loader.getController();
                messageBoxController.setMessage("please select your seat.");
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
            pricingRepository = new PricingDBRepository();
            pricingService = new PricingService(pricingRepository);
            reservationRepository = new ReservationDBRepository();
            reservationsService = new ReservationsService(reservationRepository);
            reservationsController = new ReservationsController(reservationsService, pricingService);
            int generatedID = reservationsController.handleAddReservation(userID, screeningID, pricingID, row, col);
            if (generatedID == -1) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/messagebox-view.fxml"));
                    Parent root = loader.load();
                    messageboxController messageBoxController = loader.getController();
                    messageBoxController.setMessage("failed reservation.");
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
                loadHall();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/messagebox-view.fxml"));
                    Parent root = loader.load();
                    messageboxController messageBoxController = loader.getController();
                    messageBoxController.setMessage("Reservation " + generatedID + " has been made. Please remember the ID.");
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Successful reservation");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setResizable(false);
                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void handleUpdate() throws IOException {
        pricingRepository = new PricingDBRepository();
        pricingService = new PricingService(pricingRepository);
        reservationRepository = new ReservationDBRepository();
        reservationsService = new ReservationsService(reservationRepository);
        reservationsController = new ReservationsController(reservationsService, pricingService);
        reservationID = Integer.parseInt(reservationIDField.getText());
        if (reservationID == -1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/messagebox-view.fxml"));
            Parent root = loader.load();
            messageboxController messageBoxController = loader.getController();
            messageBoxController.setMessage("Please enter your reservationID.");
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Error");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        }
        reservationsController.handleUpdateReservation(reservationID, userID, screeningID, pricingID, row, col);
        try {
            loadHall();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/messagebox-view.fxml"));
            Parent root = loader.load();
            messageboxController messageBoxController = loader.getController();
            messageBoxController.setMessage("Your new seat is " + row + "-" + col + ".");
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Reservation " + reservationID + " has been updated.");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
