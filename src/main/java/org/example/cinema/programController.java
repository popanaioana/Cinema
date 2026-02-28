package org.example.cinema;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.cinema.controller.*;
import org.example.cinema.domain.*;
import org.example.cinema.repository.db.*;
import org.example.cinema.service.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class programController {
    private int userID;
    private int screeningID = -1;
    private int movieID;
    private int parentalConsentID;
    private int movieMinAge;
    private int userAge;
    private FormatDBRepository formatDBRepository;
    private FormatService formatService;
    private FormatController formatController;
    private ParentalConsentRepository parentalConsentRepository;
    private ParentalConsentService parentalConsentService;
    private ParentalConsentController parentalConsentController;
    private ScreeningDBRepository screeningRepository;
    private ScreeningService screeningService;
    private ScreeningController screeningController;
    private GenreDBRepository genreRepository;
    private GenreService genreService;
    private GenreController genreController;
    private MovieDBRepository movieRepository;
    private MovieService movieService;
    private MovieController movieController;
    @FXML
    private ListView<String> moviesListView;
    @FXML
    private Label titleLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label formatLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private Label parentalConsentLabel;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ComboBox<String> dateTimeComboBox;
    @FXML
    private Button reservationButton;
    @FXML
    private ComboBox<String> genreFilterComboBox;
    @FXML
    private Label totalMoviesLabel;

    @FXML
    public void initialize() {
        loadMovieList();
        moviesListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldMovie, newMovie) -> {
                    if (newMovie != null) {
                        showMovieDetails(newMovie);
                    } else {
                        clearMovieDetails();
                    }
                });
        loadGenres();
        genreFilterComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                filterMoviesByGenre(newVal);
            }
        });
        dateTimeComboBox.valueProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        updateScreeningID(newValue);
                    }
                });
    }

    private void clearMovieDetails() {
        titleLabel.setText("");
        genreLabel.setText("");
        descriptionArea.setText("");
        durationLabel.setText("");
        formatLabel.setText("");
        parentalConsentLabel.setText("");
        dateTimeComboBox.getSelectionModel().clearSelection();
    }

    private void loadGenres() {
        genreRepository = new GenreDBRepository();
        genreService = new GenreService(genreRepository);
        genreController = new GenreController(genreService);
        List<Genre> genres = genreController.handleGetGenres();
        List<String> genreNames = new ArrayList<>();
        genreNames.add("All genres");
        for (Genre genre : genres) {
            genreNames.add(genre.getGenreName());
        }
        ObservableList<String> observableGenres = FXCollections.observableArrayList(genreNames);
        genreFilterComboBox.setItems(observableGenres);
        genreFilterComboBox.getSelectionModel().selectFirst();
    }

    private void filterMoviesByGenre(String genre) {
        if(genreFilterComboBox.getValue().equals("All genres") == false) {
            GenreDBRepository genreRepository = new GenreDBRepository();
            GenreService genreService = new GenreService(genreRepository);
            GenreController genreController = new GenreController(genreService);
            int genreID = genreController.handleGetGenreID(genre);
            MovieDBRepository movieRepository = new MovieDBRepository();
            MovieService movieService = new MovieService(movieRepository);
            MovieController movieController = new MovieController(movieService);
            List<Movie> movies = movieController.handleGetMoviesByGenre(genreID);
            List<String> moviesNames = new ArrayList<>();
            for (Movie movie : movies) {
                moviesNames.add(movie.getTitle());
            }
            ObservableList<String> moviesObservableList = FXCollections.observableList(moviesNames);
            moviesListView.setItems(moviesObservableList);
            int totalMovies = movies.size();
            totalMoviesLabel.setText("Total movies: " + String.valueOf(totalMovies));
        } else {
            loadMovieList();
        }
    }

    private void updateScreeningID(String dateTime) {
        screeningRepository = new ScreeningDBRepository();
        screeningService = new ScreeningService(screeningRepository);
        screeningController = new ScreeningController(screeningService);
        screeningID = screeningController.handleGetScreeningIDByDateTime(dateTime, movieID);
    }

    void loadMovieList() {
        movieRepository = new MovieDBRepository();
        movieService = new MovieService(movieRepository);
        movieController = new MovieController(movieService);
        List<Movie> movies = movieController.handleGetMovies();
        List<String> moviesNames = new ArrayList<>();
        for (Movie movie : movies) {
            moviesNames.add(movie.getTitle());
        }
        ObservableList<String> moviesObservableList = FXCollections.observableList(moviesNames);
        moviesListView.setItems(moviesObservableList);
        int totalMovies = movieController.handleGetNoOfMovies();
        totalMoviesLabel.setText("Total movies: " + Integer.toString(totalMovies));
    }

    private void showMovieDetails(String movie) {
        movieRepository = new MovieDBRepository();
        movieService = new MovieService(movieRepository);
        movieController = new MovieController(movieService);
        Movie movie1 = movieController.handleGetMovie(movie);
        movieID = movie1.getMovieID();
        parentalConsentID = movie1.getParentalConsentID();
        parentalConsentRepository = new ParentalConsentRepository();
        parentalConsentService = new  ParentalConsentService(parentalConsentRepository);
        parentalConsentController = new ParentalConsentController(parentalConsentService);
        ParentalConsent parentalConsent = parentalConsentController.handleGetParentalConsent(parentalConsentID);
        movieMinAge = parentalConsent.getAge();
        parentalConsentLabel.setText(String.valueOf(movieMinAge));
        titleLabel.setText(movie1.getTitle());
        genreRepository = new GenreDBRepository();
        genreService = new GenreService(genreRepository);
        genreController = new GenreController(genreService);
        List<Genre> genres = genreController.handleGetGenre(movie1.getMovieID());
        String allGenres = "";
        for (Genre genre : genres) {
            genre.setGenreName(genreController.handleGetGenreNameByID(genre.getGenreID()));
            allGenres += genre.getGenreName();
            allGenres += " ";
        }
        genreLabel.setText(allGenres);
        durationLabel.setText(String.valueOf(movie1.getDuration()));
        descriptionArea.setText(movie1.getDescription());
        screeningRepository = new ScreeningDBRepository();
        screeningService = new ScreeningService(screeningRepository);
        screeningController = new ScreeningController(screeningService);
        List<Screening> screenings = screeningController.handleGetScreenings(movieID);
        List<String> screeningsDatesAndTime = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Screening screening : screenings) {
            LocalDateTime screeningDateTime = LocalDateTime.of(
                    screening.getDateScreening(),
                    screening.getTimeScreening()
            );
            if (screeningDateTime.isAfter(now)) {
                screeningsDatesAndTime.add(
                        screening.getDateScreening() + " " + screening.getTimeScreening()
                );
            }
        }
        ObservableList<String> screeningsObservableList = FXCollections.observableList(screeningsDatesAndTime);
        dateTimeComboBox.setItems(screeningsObservableList);
        formatDBRepository = new FormatDBRepository();
        formatService = new FormatService(formatDBRepository);
        formatController = new FormatController(formatService);
        Format format = formatController.handleGetFormat(movie1.getFormatID());
        formatLabel.setText(format.getTypeFormat());
    }

    @FXML
    private void handleReservation() {
        if (userAge < movieMinAge) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/messagebox-view.fxml"));
                Parent root = loader.load();
                messageboxController messageBoxController = loader.getController();
                messageBoxController.setMessage("you are underage.");
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Error");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if (screeningID != -1) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org/example/cinema/cinemaHall-view.fxml")
                );
                Parent root = loader.load();
                cinemaHallController hallController = loader.getController();
                hallController.setData(userID, screeningID);
                Stage stage = new Stage();
                stage.setTitle("Cinema Hall");
                stage.setScene(new Scene(root));
                stage.show();
                Stage currentStage = (Stage)titleLabel.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setLoggedUser(int userID) {
        this.userID = userID;
    }

    public void setUserAge(int i) {
        this.userAge = i;
    }
}
