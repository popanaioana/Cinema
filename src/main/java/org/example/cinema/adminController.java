package org.example.cinema;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;
import java.util.List;

public class adminController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private Spinner<Integer> hourSpinner;
    @FXML
    private Spinner<Integer> minuteSpinner;
    @FXML
    private TableView moviesTable;
    @FXML
    private TableView screeningsTable;
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
    private ComboBox<String> comboBoxMovie;
    @FXML
    private ComboBox<Integer> comboBoxCinemaHall;
    @FXML
    private ComboBox<String> comboBoxScreeningType;
    @FXML
    private ComboBox<String> comboBoxFormat;
    @FXML
    private ComboBox<Integer> comboBoxParentalConsent;
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
    private MovieDBRepository movieRepository;
    private MovieService movieService;
    private MovieController movieController;
    private ScreeningDBRepository screeningRepository;
    private ScreeningService screeningService;
    private ScreeningController screeningController;
    private CinemaHallDBRepository cinemaHallRepository;
    private CinemaHallService cinemaHallService;
    private CinemaHallController cinemaHallController;
    private ScreeningTypeDBRepository screeningTypeRepository;
    private ScreeningTypeService screeningTypeService;
    private ScreeningTypeController screeningTypeController;
    private FormatDBRepository formatRepository;
    private FormatService formatService;
    private FormatController formatController;
    private ParentalConsentRepository parentalConsentRepository;
    private ParentalConsentService parentalConsentService;
    private ParentalConsentController parentalConsentController;
    private ReservationDBRepository reservationRepository;
    private ReservationsService reservationsService;
    private ReservationsController reservationsController;
    private PricingDBRepository pricingRepository;
    private PricingService pricingService;
    private PricingController pricingController;
    private UsersDBRepository usersRepository;
    private UsersService usersService;
    private UsersController usersController;

    @FXML
    public void initialize() {
        colMovieID.setCellValueFactory(new PropertyValueFactory<>("MovieID"));
        colFormat.setCellValueFactory(new PropertyValueFactory<>("FormatID"));
        colParentalConsent.setCellValueFactory(new PropertyValueFactory<>("ParentalConsentID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("Duration"));
        colScreeningID.setCellValueFactory(new PropertyValueFactory<>("ScreeningID"));
        colMovieSID.setCellValueFactory(new PropertyValueFactory<>("MovieID"));
        colCinemaHallID.setCellValueFactory(new PropertyValueFactory<>("CinemaHallID"));
        colTypeID.setCellValueFactory(new PropertyValueFactory<>("TypeID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("DateScreening"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("TimeScreening"));
        loadTables();
        loadComboBoxes();
        moviesTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        Movie movie = (Movie) newVal;
                        movieIDTextField.setText(
                                String.valueOf(movie.getMovieID())
                        );
                    }
                });
        screeningsTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        Screening screening = (Screening) newVal;
                        screeningIDTextField.setText(
                                String.valueOf(screening.getScreeningID())
                        );
                    }
                });
        hourSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12)
        );
        minuteSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)
        );
    }

    private void loadComboBoxes() {
        cinemaHallRepository = new CinemaHallDBRepository();
        cinemaHallService = new CinemaHallService(cinemaHallRepository);
        cinemaHallController = new CinemaHallController(cinemaHallService);
        List<CinemaHall> cinemaHalls = cinemaHallController.handleGetCinemaHalls();
        List<Integer> cinemaHallsID = new ArrayList<>();
        for (CinemaHall cinemaHall : cinemaHalls) {
            cinemaHallsID.add(cinemaHall.getCinemaHallID());
        }
        ObservableList<Integer> cinemaHallIDs = FXCollections.observableArrayList(cinemaHallsID);
        comboBoxCinemaHall.setItems(cinemaHallIDs);
        screeningTypeRepository = new ScreeningTypeDBRepository();
        screeningTypeService = new ScreeningTypeService(screeningTypeRepository);
        screeningTypeController = new ScreeningTypeController(screeningTypeService);
        List<ScreeningType> screeningTypes = screeningTypeController.handleGetScreeningTypes();
        List<String> screeningTypeNames = new ArrayList<>();
        for (ScreeningType screeningType : screeningTypes) {
            screeningTypeNames.add(screeningType.getTypeName());
        }
        ObservableList<String> screeningTypeNamesList = FXCollections.observableArrayList(screeningTypeNames);
        comboBoxScreeningType.setItems(screeningTypeNamesList);
        formatRepository = new FormatDBRepository();
        formatService = new FormatService(formatRepository);
        formatController = new FormatController(formatService);
        List<Format> formats = formatController.handleGetFormats();
        List<String> formatTypes = new ArrayList<>();
        for (Format format : formats) {
            formatTypes.add(format.getTypeFormat());
        }
        ObservableList<String> formatTypesList = FXCollections.observableArrayList(formatTypes);
        comboBoxFormat.setItems(formatTypesList);
        parentalConsentRepository = new ParentalConsentRepository();
        parentalConsentService = new ParentalConsentService(parentalConsentRepository);
        parentalConsentController = new ParentalConsentController(parentalConsentService);
        List<ParentalConsent> parentalConsents = parentalConsentController.handleGetParentalConsents();
        List<Integer> parentalConsentAge = new ArrayList<>();
        for (ParentalConsent parentalConsent : parentalConsents) {
            parentalConsentAge.add(parentalConsent.getAge());
        }
        ObservableList<Integer> parentalConsentAgeList = FXCollections.observableArrayList(parentalConsentAge);
        comboBoxParentalConsent.setItems(parentalConsentAgeList);
    }

    private void loadTables() {
        movieRepository = new MovieDBRepository();
        movieService = new MovieService(movieRepository, new MovieValidator());
        movieController = new MovieController(movieService);
        List<Movie> movies = movieController.handleGetMovies();
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(movies);
        moviesTable.setItems(observableMovies);
        List<String> movieNames = new ArrayList<>();
        for (Movie movie : movies) {
            movieNames.add(movie.getTitle());
        }
        ObservableList<String> observableMovieNames = FXCollections.observableArrayList(movieNames);
        comboBoxMovie.setItems(observableMovieNames);
        screeningRepository = new ScreeningDBRepository();
        screeningService = new ScreeningService(screeningRepository);
        screeningController = new ScreeningController(screeningService);
        List<Screening> screenings = screeningController.handleGetAllScreenings();
        ObservableList<Screening> observableScreening = FXCollections.observableArrayList(screenings);
        screeningsTable.setItems(observableScreening);
    }

    public LocalDate getDate() {
        return datePicker.getValue();
    }

    public LocalTime getTime() {
        return LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
    }

    public void addMovie() {
        formatRepository = new FormatDBRepository();
        formatService = new FormatService(formatRepository);
        formatController = new FormatController(formatService);
        Format selFormat = formatController.handleGetFormat(comboBoxFormat.getValue());
        if (selFormat != null) {
            int formatID = selFormat.getFormatID();
            parentalConsentRepository = new ParentalConsentRepository();
            parentalConsentService = new ParentalConsentService(parentalConsentRepository);
            parentalConsentController = new ParentalConsentController(parentalConsentService);
            ParentalConsent selParentalConsent =  parentalConsentController.handleGetParentalConsent(selFormat.getFormatID());
            if (selParentalConsent != null) {
                int parentalconsentID = selParentalConsent.getParentalConsentID();
                movieRepository = new MovieDBRepository();
                movieService = new MovieService(movieRepository, new MovieValidator());
                movieController = new MovieController(movieService);
                movieController.handleAddMovie(formatID, parentalconsentID, titleTextField.getText(), descriptionTextField.getText(), Integer.parseInt(durationTextField.getText()));
                movieFeedbackLabel.setText("movie added successfully.");
                clearMovieInfo();
                loadTables();
                loadTables();
            } else {
                movieFeedbackLabel.setText("missing parental consent.");
            }
        } else {
            movieFeedbackLabel.setText("missing format.");
        }
    }

    public void updateMovie() {
        int movieID = Integer.parseInt(movieIDTextField.getText());
        if (movieID != 0) {
            formatRepository = new FormatDBRepository();
            formatService = new FormatService(formatRepository);
            formatController = new FormatController(formatService);
            Format selFormat = formatController.handleGetFormat(comboBoxFormat.getValue());
            if (selFormat != null) {
                int formatID = selFormat.getFormatID();
                parentalConsentRepository = new ParentalConsentRepository();
                parentalConsentService = new ParentalConsentService(parentalConsentRepository);
                parentalConsentController = new ParentalConsentController(parentalConsentService);
                ParentalConsent selParentalConsent =  parentalConsentController.handleGetParentalConsent(selFormat.getFormatID());
                if (selParentalConsent != null) {
                    int parentalconsentID = selParentalConsent.getParentalConsentID();
                    movieRepository = new MovieDBRepository();
                    movieService = new MovieService(movieRepository, new MovieValidator());
                    movieController = new MovieController(movieService);
                    movieController.handleUpdateMovie(movieID, formatID, parentalconsentID, titleTextField.getText(), descriptionTextField.getText(), Integer.parseInt(durationTextField.getText()));
                    movieFeedbackLabel.setText("movie updated successfully.");
                    clearMovieInfo();
                    loadTables();
                    loadTables();
                } else {
                    movieFeedbackLabel.setText("missing parental consent.");
                }
            } else {
                movieFeedbackLabel.setText("missing format.");
            }
        } else {
            movieFeedbackLabel.setText("enter valid movie ID.");
        }
    }

    public void deleteMovie() {
        int movieID = Integer.parseInt(movieIDTextField.getText());
        if (movieID != 0) {
            movieRepository =  new MovieDBRepository();
            movieService = new MovieService(movieRepository, new MovieValidator());
            movieController = new MovieController(movieService);
            movieController.handleDeleteMovie(movieID);
            movieFeedbackLabel.setText("movie deleted successfully.");
            clearMovieInfo();
            loadTables();
            loadComboBoxes();
        } else {
            movieFeedbackLabel.setText("enter valid movie ID.");
        }
    }

    private void clearMovieInfo() {
        movieIDTextField.clear();
        titleTextField.clear();
        descriptionTextField.clear();
        durationTextField.clear();
    }

    private void clearScreeningInfo() {
        screeningIDTextField.clear();
        datePicker.setValue(null);
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));
    }

    public void addScreening() {
        String movieName = comboBoxMovie.getValue();
        if (movieName.equals("") == false) {
            movieRepository = new MovieDBRepository();
            movieService = new MovieService(movieRepository, new MovieValidator());
            movieController = new MovieController(movieService);
            Movie selMovie = movieController.handleGetMovie(movieName);
            int selMovieID = selMovie.getMovieID();
            int selCinemaHallId = comboBoxCinemaHall.getValue();
            if (selCinemaHallId != 0) {
                String selScreeningTypeName = comboBoxScreeningType.getValue();
                if (selScreeningTypeName.equals("") == false) {
                    screeningTypeRepository = new ScreeningTypeDBRepository();
                    screeningTypeService = new ScreeningTypeService(screeningTypeRepository);
                    screeningTypeController = new ScreeningTypeController(screeningTypeService);
                    ScreeningType selScreeningType = screeningTypeController.handleGetScreeningType(selScreeningTypeName);
                    int selScreeningTypeID = selScreeningType.getScreeningTypeID();
                    screeningRepository = new ScreeningDBRepository();
                    screeningService = new ScreeningService(screeningRepository, new ScreeningValidator());
                    screeningController = new ScreeningController(screeningService);
                    LocalDate selDate = getDate();
                    LocalTime selTime = getTime();
                    screeningController.handleAddScreening(selMovieID, selCinemaHallId, selDate, selTime, selScreeningTypeID);
                    clearScreeningInfo();
                    loadTables();
                    screeningFeedbackLabel.setText("screening successfully added.");
                } else {
                    screeningFeedbackLabel.setText("select a screening type.");
                }
            } else {
                screeningFeedbackLabel.setText("select a cinema hall.");
            }
        } else {
            screeningFeedbackLabel.setText("select movie.");
        }
    }

    public void updateScreening() {
        int screeningID = Integer.parseInt(screeningIDTextField.getText());
        if (screeningID != 0) {
            String movieName = comboBoxMovie.getValue();
            if (movieName.equals("") == false) {
                movieRepository = new MovieDBRepository();
                movieService = new MovieService(movieRepository, new MovieValidator());
                movieController = new MovieController(movieService);
                Movie selMovie = movieController.handleGetMovie(movieName);
                int selMovieID = selMovie.getMovieID();
                int selCinemaHallId = comboBoxCinemaHall.getValue();
                if (selCinemaHallId != 0) {
                    String selScreeningTypeName = comboBoxScreeningType.getValue();
                    if (selScreeningTypeName.equals("") == false) {
                        screeningTypeRepository = new ScreeningTypeDBRepository();
                        screeningTypeService = new ScreeningTypeService(screeningTypeRepository);
                        screeningTypeController = new ScreeningTypeController(screeningTypeService);
                        ScreeningType selScreeningType = screeningTypeController.handleGetScreeningType(selScreeningTypeName);
                        int selScreeningTypeID = selScreeningType.getScreeningTypeID();
                        screeningRepository = new ScreeningDBRepository();
                        screeningService = new ScreeningService(screeningRepository, new ScreeningValidator());
                        screeningController = new ScreeningController(screeningService);
                        LocalDate selDate = getDate();
                        LocalTime selTime = getTime();
                        screeningController.handleUpdateScreening(screeningID, selMovieID, selCinemaHallId, selDate, selTime, selScreeningTypeID);
                        clearScreeningInfo();
                        loadTables();
                        screeningFeedbackLabel.setText("screening successfully updated.");
                    } else {
                        screeningFeedbackLabel.setText("select a screening type.");
                    }
                } else {
                    screeningFeedbackLabel.setText("select a cinema hall.");
                }
            } else {
                screeningFeedbackLabel.setText("select movie.");
            }
        } else {
            screeningFeedbackLabel.setText("enter valid screening ID.");
        }
    }

    public void deleteScreening() {
        int screeningID = Integer.parseInt(screeningIDTextField.getText());
        if (screeningID != 0) {
            screeningRepository = new ScreeningDBRepository();
            screeningService = new ScreeningService(screeningRepository, new ScreeningValidator());
            screeningController = new ScreeningController(screeningService);
            screeningController.handleDeleteScreening(screeningID);
            screeningFeedbackLabel.setText("screening deleted successfully.");
            clearScreeningInfo();
            loadTables();
        } else {
            screeningFeedbackLabel.setText("enter valid screening ID.");
        }
    }

    public void findReservation() {
        int reservationID = Integer.parseInt(reservationIDTextField.getText());
        if (reservationID != 0) {
            pricingRepository = new PricingDBRepository();
            pricingService = new PricingService(pricingRepository);
            pricingController = new PricingController(pricingService);
            reservationRepository = new ReservationDBRepository();
            reservationsService = new ReservationsService(reservationRepository);
            reservationsController = new ReservationsController(reservationsService, pricingService);
            Reservations reservation = reservationsController.handleGetReservation(reservationID);
            if (reservation != null) {
                feedbackReservationLabel.setText("reservation successfully found.");
                seatLabel.setText(String.valueOf(reservation.getRowReservation()) + "-" + reservation.getColumnReservation());
                int clientID = reservation.getClientID();
                usersRepository = new UsersDBRepository();
                usersService = new UsersService(usersRepository, new UsersValidator(), new ClientValidator());
                usersController = new UsersController(usersService);
                Users client = usersController.handleGetUser(clientID);
                clientLabel.setText(client.getFirstName() + " " + client.getLastName());
                int priceID = reservation.getPriceID();
                Pricing price = pricingController.handleGetPricing(priceID);
                priceLabel.setText(String.valueOf(price.getPrice()));
                int screeningID = reservation.getScreeningID();
                screeningRepository = new ScreeningDBRepository();
                screeningService = new ScreeningService(screeningRepository, new ScreeningValidator());
                screeningController = new ScreeningController(screeningService);
                Screening screening = screeningController.handleGetScreening(screeningID);
                dateLabel.setText(String.valueOf(screening.getDateScreening()));
                timeLabel.setText(String.valueOf(screening.getTimeScreening()));
                cinemaHallLabel.setText(String.valueOf(screening.getCinemaHallID()));
                int movieID = screening.getMovieID();
                movieRepository = new MovieDBRepository();
                movieService = new MovieService(movieRepository, new MovieValidator());
                movieController = new MovieController(movieService);
                Movie movie = movieController.handleGetMovie(movieID);
                movieLabel.setText(movie.getTitle());
                reservationIDTextField.clear();
            } else {
                return;
            }
        } else {
            return;
        }
    }

    public void deleteReservation() {
        int reservationID = Integer.parseInt(reservationIDTextField.getText());
        if (reservationID != 0) {
            findReservation();
            reservationRepository = new ReservationDBRepository();
            reservationsService = new ReservationsService(reservationRepository);
            reservationsController.handleDeleteReservation(reservationID);
            feedbackReservationLabel.setText("reservation successfully deleted.");
        } else {
            return;
        }
    }
}
