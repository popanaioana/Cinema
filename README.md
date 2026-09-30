# Cinema Management System

A desktop cinema management and reservation application developed in Java using JavaFX and Microsoft SQL Server.

The application provides separate functionality for customers and cinema administrators. Customers can create an account, browse available movies and screenings, select seats, make reservations, and update existing reservations. Administrators can manage movies and screenings and search for or delete reservations.

## Features

### Customer

- Create a customer account
- Log in to the application
- Browse available movies
- Filter movies by genre
- View movie information, including format, duration, genres, description, and age restriction
- View available screenings
- Select available seats from a visual cinema hall
- Make reservations
- Update the seat of an existing reservation
- Automatic ticket pricing based on customer type and screening type

### Administrator

- Log in using an administrator account
- View all movies and screenings
- Add, update, and delete movies
- Add, update, and delete screenings
- Search for reservations by ID
- View reservation details
- Delete reservations

## Architecture

The project follows a layered architecture to separate application responsibilities:

```text
JavaFX UI
    ↓
Controllers
    ↓
Services
    ↓
Repository Interfaces
    ↓
Database Repositories
    ↓
Microsoft SQL Server
```

The main layers are:

- **Domain** — application entities such as Movie, Screening, User, Reservation, and Pricing
- **Controllers** — connect the user interface with the application logic
- **Services** — contain application logic and coordinate repository operations
- **Validators** — validate user input and domain data
- **Repository Interfaces** — define database operations
- **Database Repositories** — provide JDBC implementations for SQL Server
- **Configuration** — manages database connection configuration

## Technologies

- Java
- JavaFX
- FXML
- Maven
- JDBC
- Microsoft SQL Server
- Git

## Database

The application uses Microsoft SQL Server.

Database credentials are not stored in the source code. The application reads the connection configuration from environment variables.

The following environment variables must be configured before running the application:

```text
CINEMA_DB_URL
CINEMA_DB_USER
CINEMA_DB_PASSWORD
```

Example database URL:

```text
jdbc:sqlserver://localhost:1433;databaseName=CinemaDB;encrypt=true;trustServerCertificate=true
```

Do not store database passwords or other credentials in the repository.

## Project Structure

```text
src/main/java/org/example/cinema/
├── config/
├── controller/
├── domain/
├── repository/
│   ├── db/
│   └── interfaces/
├── service/
├── validators/
├── MainApplication.java
├── loginController.java
├── signupController.java
├── programController.java
├── cinemaHallController.java
├── adminController.java
└── messageboxController.java
```

JavaFX views are stored under:

```text
src/main/resources/org/example/cinema/
```

## Running the Application

### Prerequisites

Make sure you have:

- Java installed
- Maven installed or use the included Maven Wrapper
- Microsoft SQL Server running
- A configured Cinema database

### 1. Clone the repository

```bash
git clone https://github.com/popanaioana/Cinema.git
cd Cinema
```

### 2. Configure the database

Create the required database and configure:

```text
CINEMA_DB_URL
CINEMA_DB_USER
CINEMA_DB_PASSWORD
```

### 3. Run with Maven

On Windows:

```bash
.\mvnw.cmd javafx:run
```

On macOS/Linux:

```bash
./mvnw javafx:run
```

Alternatively, the application can be started from an IDE by running `MainApplication`.

## Future Improvements

- Secure password hashing and authentication
- Automated unit and integration tests
- Improved exception handling and logging
- Additional database constraints
- Enhanced JavaFX user interface
- Reservation history for customers

## Author

Developed by [Ana-Ioana Pop](https://github.com/popanaioana)