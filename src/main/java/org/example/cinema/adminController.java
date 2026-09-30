package org.example.cinema;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.cinema.controller.*;
import org.example.cinema.domain.*;
import org.example.cinema.repository.db.*;
import org.example.cinema.service.*;
import org.example.cinema.validators.ClientValidator;
import org.example.cinema.validators.MovieValidator;
import org.example.cinema.validators.ScreeningValidator;
import org.example.cinema.validators.UsersValidator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class adminController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private Spinner<Integer> hourSpinner;
    @FXML
    private Spinner<Integer> minuteSpinner;
    @FXML
    private TableView<Movie> moviesTable;
    @FXML
    private TableView<Screening> screeningsTable;
    @FXML
    private TableColumn<Movie, Integer> colMovieID;
    @FXML
    private TableColumn<Movie, Integer> colFormat;
    @FXML
    private TableColumn<Movie, Integer> colParentalConsent;
    @FXML
    private TableColumn<Movie, String> colTitle;
    @FXML
    private TableColumn<Movie, String> colDescription;
    @FXML
    private TableColumn<Movie, Integer> colDuration;
    @FXML
    private TableColumn<Screening, Integer> colScreeningID;
    @FXML
    private TableColumn<Screening, Integer> colMovieSID;
    @FXML
    private TableColumn<Screening, Integer> colCinemaHallID;
    @FXML
    private TableColumn<Screening, Integer> colTypeID;
    @FXML
    private TableColumn<Screening, LocalDate> colDate;
    @FXML
    private TableColumn<Screening, LocalTime> colTime;
    @FXML
    private ComboBox<Movie> comboBoxMovie;
    @FXML
    private ComboBox<CinemaHall> comboBoxCinemaHall;
    @FXML
    private ComboBox<ScreeningType> comboBoxScreeningType;
    @FXML
    private ComboBox<Format> comboBoxFormat;
    @FXML
    private ComboBox<ParentalConsent> comboBoxParentalConsent;
    @FXML
    private TextField movieIDTextField;
    @FXML
    private TextField screeningIDTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField durationTextField;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private Label movieFeedbackLabel;
    @FXML
    private Label screeningFeedbackLabel;
    @FXML
    private TextField reservationIDTextField;
    @FXML
    private Label clientLabel;
    @FXML
    private Label movieLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label cinemaHallLabel;
    @FXML
    private Label seatLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label feedbackReservationLabel;
    private final MovieController movieController;
    private final ScreeningController screeningController;
    private final CinemaHallController cinemaHallController;
    private final ScreeningTypeController screeningTypeController;
    private final FormatController formatController;
    private final ParentalConsentController parentalConsentController;
    private final ReservationsController reservationsController;
    private final PricingController pricingController;
    private final UsersController usersController;

    public adminController() {
        MovieService movieService = new MovieService(new MovieDBRepository(), new MovieValidator());
        this.movieController = new MovieController(movieService);
        ScreeningService screeningService = new ScreeningService(new ScreeningDBRepository(), new ScreeningValidator());
        this.screeningController = new ScreeningController(screeningService);
        CinemaHallService cinemaHallService = new CinemaHallService(new CinemaHallDBRepository());
        this.cinemaHallController = new CinemaHallController(cinemaHallService);
        ScreeningTypeService screeningTypeService = new ScreeningTypeService(new ScreeningTypeDBRepository());
        this.screeningTypeController = new ScreeningTypeController(screeningTypeService);
        FormatService formatService = new FormatService(new FormatDBRepository());
        this.formatController = new FormatController(formatService);
        ParentalConsentService parentalConsentService = new ParentalConsentService(new ParentalConsentRepository());
        this.parentalConsentController = new ParentalConsentController(parentalConsentService);
        ReservationsService reservationsService = new ReservationsService(new ReservationDBRepository());
        this.reservationsController = new ReservationsController(reservationsService);
        PricingService pricingService = new PricingService(new PricingDBRepository());
        this.pricingController = new PricingController(pricingService);
        UsersService usersService = new UsersService(new UsersDBRepository(), new UsersValidator(), new ClientValidator());
        this.usersController = new UsersController(usersService);
    }

    @FXML
    public void initialize() {
        configureTables();
        configureSpinners();
        configureComboBoxes();
        loadTables();
        loadComboBoxes();
        moviesTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        movieIDTextField.setText(
                                String.valueOf(newValue.getMovieID())
                        );
                    }
                });
        screeningsTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        screeningIDTextField.setText(
                                String.valueOf(newValue.getScreeningID())
                        );
                    }
                });
    }

    private void configureTables() {
        colMovieID.setCellValueFactory(new PropertyValueFactory<>("movieID"));
        colFormat.setCellValueFactory(new PropertyValueFactory<>("formatID"));
        colParentalConsent.setCellValueFactory(new PropertyValueFactory<>("parentalConsentID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colScreeningID.setCellValueFactory(new PropertyValueFactory<>("screeningID"));
        colMovieSID.setCellValueFactory(new PropertyValueFactory<>("movieID"));
        colCinemaHallID.setCellValueFactory(new PropertyValueFactory<>("cinemaHallID"));
        colTypeID.setCellValueFactory(new PropertyValueFactory<>("typeID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateScreening"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("timeScreening"));
    }

    private void configureSpinners() {
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    }

    private void configureComboBoxes() {
        comboBoxMovie.setCellFactory(listView -> createMovieCell());
        comboBoxMovie.setButtonCell(createMovieCell());
        comboBoxCinemaHall.setCellFactory(listView -> createCinemaHallCell());
        comboBoxCinemaHall.setButtonCell(createCinemaHallCell());
        comboBoxScreeningType.setCellFactory(listView -> createScreeningTypeCell());
        comboBoxScreeningType.setButtonCell(createScreeningTypeCell());
        comboBoxFormat.setCellFactory(listView -> createFormatCell());
        comboBoxFormat.setButtonCell(createFormatCell());
        comboBoxParentalConsent.setCellFactory(listView -> createParentalConsentCell());
        comboBoxParentalConsent.setButtonCell(createParentalConsentCell());
    }

    private ListCell<Movie> createMovieCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);
                setText(empty || movie == null ? null : movie.getTitle());
            }
        };
    }

    private ListCell<CinemaHall> createCinemaHallCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(CinemaHall cinemaHall, boolean empty) {
                super.updateItem(cinemaHall, empty);
                setText(empty || cinemaHall == null ? null : String.valueOf(cinemaHall.getCinemaHallID()));
            }
        };
    }

    private ListCell<ScreeningType> createScreeningTypeCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(ScreeningType screeningType, boolean empty) {
                super.updateItem(screeningType, empty);
                setText(empty || screeningType == null ? null : screeningType.getTypeName());
            }
        };
    }

    private ListCell<Format> createFormatCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Format format, boolean empty
            ) {
                super.updateItem(format, empty);
                setText(empty || format == null ? null : format.getTypeFormat());
            }
        };
    }

    private ListCell<ParentalConsent> createParentalConsentCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(ParentalConsent parentalConsent, boolean empty) {
                super.updateItem(parentalConsent, empty);
                setText(empty || parentalConsent == null ? null : String.valueOf(parentalConsent.getAge()));
            }
        };
    }

    private void loadComboBoxes() {
        comboBoxCinemaHall.setItems(FXCollections.observableArrayList(cinemaHallController.handleGetCinemaHalls()));
        comboBoxScreeningType.setItems(FXCollections.observableArrayList(screeningTypeController.handleGetScreeningTypes()));
        comboBoxFormat.setItems(FXCollections.observableArrayList(formatController.handleGetFormats()));
        comboBoxParentalConsent.setItems(FXCollections.observableArrayList(parentalConsentController.handleGetParentalConsents()));
        comboBoxMovie.setItems(FXCollections.observableArrayList(movieController.handleGetMovies()));
    }

    private void loadTables() {
        List<Movie> movies = movieController.handleGetMovies();
        moviesTable.setItems(FXCollections.observableArrayList(movies));
        List<Screening> screenings = screeningController.handleGetAllScreenings();
        screeningsTable.setItems(FXCollections.observableArrayList(screenings));
        comboBoxMovie.setItems(FXCollections.observableArrayList(movies));
    }

    private LocalDate getDate() {
        return datePicker.getValue();
    }

    private LocalTime getTime() {
        return LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
    }

    @FXML
    public void addMovie() {
        try {
            Format selectedFormat = comboBoxFormat.getValue();
            ParentalConsent selectedParentalConsent = comboBoxParentalConsent.getValue();
            if (selectedFormat == null) {
                movieFeedbackLabel.setText("Select a format.");
                return;
            }
            if (selectedParentalConsent == null) {
                movieFeedbackLabel.setText("Select a parental consent.");
                return;
            }
            int duration = Integer.parseInt(durationTextField.getText());
            movieController.handleAddMovie(selectedFormat.getFormatID(), selectedParentalConsent.getParentalConsentID(), titleTextField.getText(), descriptionTextField.getText(), duration);
            movieFeedbackLabel.setText("Movie added successfully.");
            clearMovieInfo();
            loadTables();
        } catch (NumberFormatException e) {
            movieFeedbackLabel.setText("Duration must be a valid number.");
        } catch (IllegalArgumentException e) {
            movieFeedbackLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void updateMovie() {
        try {
            if (movieIDTextField.getText().isBlank()) {
                movieFeedbackLabel.setText("Select a movie.");
                return;
            }
            int movieID = Integer.parseInt(movieIDTextField.getText());
            Format selectedFormat = comboBoxFormat.getValue();
            ParentalConsent selectedParentalConsent = comboBoxParentalConsent.getValue();
            if (selectedFormat == null) {
                movieFeedbackLabel.setText("Select a format.");
                return;
            }
            if (selectedParentalConsent == null) {
                movieFeedbackLabel.setText("Select a parental consent.");
                return;
            }
            int duration = Integer.parseInt(durationTextField.getText());
            movieController.handleUpdateMovie(movieID, selectedFormat.getFormatID(), selectedParentalConsent.getParentalConsentID(), titleTextField.getText(), descriptionTextField.getText(), duration);
            movieFeedbackLabel.setText("Movie updated successfully.");
            clearMovieInfo();
            loadTables();
        } catch (NumberFormatException e) {
            movieFeedbackLabel.setText("Movie ID and duration must be valid numbers.");
        } catch (IllegalArgumentException e) {
            movieFeedbackLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void deleteMovie() {
        try {
            if (movieIDTextField.getText().isBlank()) {
                movieFeedbackLabel.setText("Select a movie.");
                return;
            }
            int movieID = Integer.parseInt(movieIDTextField.getText());
            movieController.handleDeleteMovie(movieID);
            movieFeedbackLabel.setText("Movie deleted successfully.");
            clearMovieInfo();
            loadTables();
            loadComboBoxes();
        } catch (NumberFormatException e) {
            movieFeedbackLabel.setText("Movie ID must be a valid number.");
        }
    }

    private void clearMovieInfo() {
        movieIDTextField.clear();
        titleTextField.clear();
        descriptionTextField.clear();
        durationTextField.clear();
        comboBoxFormat.getSelectionModel().clearSelection();
        comboBoxParentalConsent.getSelectionModel().clearSelection();
    }

    private void clearScreeningInfo() {
        screeningIDTextField.clear();
        datePicker.setValue(null);
        hourSpinner.getValueFactory().setValue(12);
        minuteSpinner.getValueFactory().setValue(0);
        comboBoxMovie.getSelectionModel().clearSelection();
        comboBoxCinemaHall.getSelectionModel().clearSelection();
        comboBoxScreeningType.getSelectionModel().clearSelection();
    }

    @FXML
    public void addScreening() {
        try {
            Movie selectedMovie = comboBoxMovie.getValue();
            CinemaHall selectedCinemaHall = comboBoxCinemaHall.getValue();
            ScreeningType selectedScreeningType = comboBoxScreeningType.getValue();
            LocalDate selectedDate = getDate();
            if (selectedMovie == null) {
                screeningFeedbackLabel.setText("Select a movie.");
                return;
            }
            if (selectedCinemaHall == null) {
                screeningFeedbackLabel.setText("Select a cinema hall.");
                return;
            }
            if (selectedScreeningType == null) {
                screeningFeedbackLabel.setText("Select a screening type.");
                return;
            }
            if (selectedDate == null) {
                screeningFeedbackLabel.setText("Select a screening date.");
                return;
            }
            screeningController.handleAddScreening(selectedMovie.getMovieID(), selectedCinemaHall.getCinemaHallID(), selectedDate, getTime(), selectedScreeningType.getScreeningTypeID());
            screeningFeedbackLabel.setText("Screening successfully added.");
            clearScreeningInfo();
            loadTables();
        } catch (IllegalArgumentException e) {
            screeningFeedbackLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void updateScreening() {
        try {
            if (screeningIDTextField.getText().isBlank()) {
                screeningFeedbackLabel.setText("Select a screening.");
                return;
            }
            int screeningID = Integer.parseInt(screeningIDTextField.getText());
            Movie selectedMovie = comboBoxMovie.getValue();
            CinemaHall selectedCinemaHall = comboBoxCinemaHall.getValue();
            ScreeningType selectedScreeningType = comboBoxScreeningType.getValue();
            LocalDate selectedDate = getDate();
            if (selectedMovie == null || selectedCinemaHall == null || selectedScreeningType == null || selectedDate == null) {
                screeningFeedbackLabel.setText("Complete all screening fields.");
                return;
            }
            screeningController.handleUpdateScreening(screeningID, selectedMovie.getMovieID(), selectedCinemaHall.getCinemaHallID(), selectedDate, getTime(), selectedScreeningType.getScreeningTypeID());
            screeningFeedbackLabel.setText("Screening successfully updated.");
            clearScreeningInfo();
            loadTables();
        } catch (NumberFormatException e) {
            screeningFeedbackLabel.setText("Screening ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            screeningFeedbackLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void deleteScreening() {
        try {
            if (screeningIDTextField.getText().isBlank()) {
                screeningFeedbackLabel.setText("Select a screening.");
                return;
            }
            int screeningID = Integer.parseInt(screeningIDTextField.getText());
            screeningController.handleDeleteScreening(screeningID);
            screeningFeedbackLabel.setText("Screening deleted successfully.");
            clearScreeningInfo();
            loadTables();
        } catch (NumberFormatException e) {
            screeningFeedbackLabel.setText("Screening ID must be a valid number.");
        }
    }

    @FXML
    public void findReservation() {
        try {
            if (reservationIDTextField.getText().isBlank()) {
                clearReservationInfo();
                feedbackReservationLabel.setText("Enter a reservation ID.");
                return;
            }
            int reservationID = Integer.parseInt(reservationIDTextField.getText());
            Reservations reservation = reservationsController.handleGetReservation(reservationID);
            if (reservation == null) {
                clearReservationInfo();
                feedbackReservationLabel.setText("Reservation not found.");
                return;
            }
            Users client = usersController.handleGetUser(reservation.getClientID());
            Pricing price = pricingController.handleGetPricing(reservation.getPriceID());
            Screening screening = screeningController.handleGetScreening(reservation.getScreeningID());
            Movie movie = movieController.handleGetMovie(screening.getMovieID());
            clientLabel.setText(client.getFirstName() + " " + client.getLastName());
            movieLabel.setText(movie.getTitle());
            dateLabel.setText(String.valueOf(screening.getDateScreening()));
            timeLabel.setText(String.valueOf(screening.getTimeScreening()));
            cinemaHallLabel.setText(String.valueOf(screening.getCinemaHallID()));
            seatLabel.setText(reservation.getRowReservation() + "-" + reservation.getColumnReservation());
            priceLabel.setText(String.valueOf(price.getPrice()));
            feedbackReservationLabel.setText("Reservation successfully found.");
        } catch (NumberFormatException e) {
            clearReservationInfo();
            feedbackReservationLabel.setText("Reservation ID must be a valid number.");
        }
    }

    @FXML
    public void deleteReservation() {
        try {
            if (reservationIDTextField.getText().isBlank()) {
                clearReservationInfo();
                feedbackReservationLabel.setText("Enter a reservation ID.");
                return;
            }
            int reservationID = Integer.parseInt(reservationIDTextField.getText());
            Reservations reservation = reservationsController.handleGetReservation(reservationID);
            if (reservation == null) {
                clearReservationInfo();
                feedbackReservationLabel.setText("Reservation not found.");
                return;
            }
            reservationsController.handleDeleteReservation(reservationID);
            clearReservationInfo();
            reservationIDTextField.clear();
            feedbackReservationLabel.setText("Reservation successfully deleted.");
        } catch (NumberFormatException e) {
            clearReservationInfo();
            feedbackReservationLabel.setText("Reservation ID must be a valid number.");
        }
    }

    private void clearReservationInfo() {
        clientLabel.setText("");
        movieLabel.setText("");
        dateLabel.setText("");
        timeLabel.setText("");
        cinemaHallLabel.setText("");
        seatLabel.setText("");
        priceLabel.setText("");
    }
}