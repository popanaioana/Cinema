package org.example.cinema;

import javafx.collections.FXCollections;
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
import org.example.cinema.validators.MovieValidator;
import org.example.cinema.validators.ScreeningValidator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class programController {
    private int userID;
    private int screeningID = -1;
    private int movieID;
    private int movieMinAge;
    private int userAge;
    private final FormatController formatController;
    private final ParentalConsentController parentalConsentController;
    private final ScreeningController screeningController;
    private final GenreController genreController;
    private final MovieController movieController;
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

    public programController() {
        FormatService formatService = new FormatService(new FormatDBRepository());
        this.formatController = new FormatController(formatService);
        ParentalConsentService parentalConsentService = new ParentalConsentService(new ParentalConsentRepository());
        this.parentalConsentController = new ParentalConsentController(parentalConsentService);
        ScreeningService screeningService = new ScreeningService(new ScreeningDBRepository(), new ScreeningValidator());
        this.screeningController = new ScreeningController(screeningService);
        GenreService genreService = new GenreService(new GenreDBRepository());
        this.genreController = new GenreController(genreService);
        MovieService movieService = new MovieService(new MovieDBRepository(), new MovieValidator());
        this.movieController = new MovieController(movieService);
    }

    @FXML
    public void initialize() {
        loadMovieList();
        loadGenres();
        moviesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldMovie, newMovie) -> {
            if (newMovie != null) {
                showMovieDetails(newMovie);
            } else {
                clearMovieDetails();
            }
        });
        genreFilterComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                filterMoviesByGenre(newValue);
            }
        });
        dateTimeComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                updateScreeningID(newValue);
            } else {
                screeningID = -1;
            }
        });
    }

    private void loadMovieList() {
        List<Movie> movies = movieController.handleGetMovies();
        List<String> movieNames = new ArrayList<>();
        for (Movie movie : movies) {
            movieNames.add(movie.getTitle());
        }
        moviesListView.setItems(FXCollections.observableArrayList(movieNames));
        totalMoviesLabel.setText("Total movies: " + movies.size());
    }

    private void loadGenres() {
        List<Genre> genres = genreController.handleGetGenres();
        List<String> genreNames = new ArrayList<>();
        genreNames.add("All genres");
        for (Genre genre : genres) {
            genreNames.add(genre.getGenreName());
        }
        genreFilterComboBox.setItems(FXCollections.observableArrayList(genreNames));
        genreFilterComboBox.getSelectionModel().selectFirst();
    }

    private void filterMoviesByGenre(String genreName) {
        if ("All genres".equals(genreName)) {
            loadMovieList();
            return;
        }
        int genreID = genreController.handleGetGenreID(genreName);
        List<Movie> movies = movieController.handleGetMoviesByGenre(genreID);
        List<String> movieNames = new ArrayList<>();
        for (Movie movie : movies) {
            movieNames.add(movie.getTitle());
        }
        moviesListView.setItems(FXCollections.observableArrayList(movieNames));
        totalMoviesLabel.setText("Total movies: " + movies.size());
    }

    private void showMovieDetails(String movieTitle) {
        Movie movie = movieController.handleGetMovie(movieTitle);
        if (movie == null) {
            clearMovieDetails();
            return;
        }
        movieID = movie.getMovieID();
        titleLabel.setText(movie.getTitle());
        durationLabel.setText(String.valueOf(movie.getDuration()));
        descriptionArea.setText(movie.getDescription());
        loadParentalConsent(movie);
        loadGenresForMovie(movie);
        loadFormat(movie);
        loadScreenings(movie);
    }

    private void loadParentalConsent(Movie movie) {
        ParentalConsent parentalConsent = parentalConsentController.handleGetParentalConsent(movie.getParentalConsentID());
        if (parentalConsent == null) {
            movieMinAge = 0;
            parentalConsentLabel.setText("");
            return;
        }
        movieMinAge = parentalConsent.getAge();
        parentalConsentLabel.setText(String.valueOf(movieMinAge));
    }

    private void loadGenresForMovie(Movie movie) {
        List<Genre> genres = genreController.handleGetGenres(movie.getMovieID());
        StringBuilder allGenres = new StringBuilder();
        for (Genre genre : genres) {
            if (!allGenres.isEmpty()) {
                allGenres.append(", ");
            }
            allGenres.append(genre.getGenreName());
        }
        genreLabel.setText(allGenres.toString());
    }

    private void loadFormat(Movie movie) {
        Format format = formatController.handleGetFormat(movie.getFormatID());
        if (format != null) {
            formatLabel.setText(format.getTypeFormat());
        } else {
            formatLabel.setText("");
        }
    }

    private void loadScreenings(Movie movie) {
        List<Screening> screenings = screeningController.handleGetScreenings(movie.getMovieID());
        List<String> screeningDatesAndTimes = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Screening screening : screenings) {
            LocalDateTime screeningDateTime = LocalDateTime.of(screening.getDateScreening(), screening.getTimeScreening());
            if (screeningDateTime.isAfter(now)) {
                screeningDatesAndTimes.add(screening.getDateScreening() + " " + screening.getTimeScreening());
            }
        }
        dateTimeComboBox.setItems(FXCollections.observableArrayList(screeningDatesAndTimes));
        dateTimeComboBox.getSelectionModel().clearSelection();
        screeningID = -1;
    }

    private void updateScreeningID(String dateTime) {
        screeningID = screeningController.handleGetScreeningIDByDateTime(dateTime, movieID);
    }

    private void clearMovieDetails() {
        movieID = 0;
        movieMinAge = 0;
        screeningID = -1;
        titleLabel.setText("");
        genreLabel.setText("");
        descriptionArea.setText("");
        durationLabel.setText("");
        formatLabel.setText("");
        parentalConsentLabel.setText("");
        dateTimeComboBox.getItems().clear();
        dateTimeComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleReservation() {
        if (movieID == 0) {
            showMessage("Select a movie.", "Error");
            return;
        }
        if (userAge < movieMinAge) {
            showMessage("You are underage for this movie.", "Error");
            return;
        }
        if (screeningID == -1) {
            showMessage("Select a screening.", "Error");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cinema/cinemaHall-view.fxml"));
            Parent root = loader.load();
            cinemaHallController hallController = loader.getController();
            hallController.setData(userID, screeningID);
            Stage stage = new Stage();
            stage.setTitle("Cinema Hall");
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = (Stage) titleLabel.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    public void setLoggedUser(int userID) {
        this.userID = userID;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}