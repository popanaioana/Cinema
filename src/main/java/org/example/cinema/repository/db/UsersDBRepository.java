package org.example.cinema.repository.db;

import org.example.cinema.config.DatabaseConfig;
import org.example.cinema.domain.AdminCinema;
import org.example.cinema.domain.Client;
import org.example.cinema.domain.Users;
import org.example.cinema.repository.interfaces.IUsersRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsersDBRepository implements IUsersRepository {

    @Override
    public List<Users> getUsers() {
        List<Users> users = new ArrayList<>();
        String clientSql = "SELECT u.UserID, u.FirstName, u.LastName, u.Email, u.PhoneNumber, u.DateOfBirth, u.UserName, u.UserPassword, c.TypeID " +
                "FROM Users u JOIN Client c ON u.UserID = c.ClientID";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(clientSql)) {
            while (resultSet.next()) {
                users.add(mapClient(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String adminSql = "SELECT u.UserID, u.FirstName, u.LastName, u.Email, u.PhoneNumber, u.DateOfBirth, u.UserName, u.UserPassword, a.Salary, a.DateOfEmployement " +
                "FROM Users u JOIN AdminCinema a ON u.UserID = a.AdminID";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(adminSql)) {
            while (resultSet.next()) {
                users.add(mapAdmin(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Users getUser(String username, String userPassword) {
        String clientSql = "SELECT u.UserID, u.FirstName, u.LastName, u.Email, u.PhoneNumber, u.DateOfBirth, u.UserName, u.UserPassword, c.TypeID " +
                "FROM Users u JOIN Client c ON u.UserID = c.ClientID WHERE u.UserName = ? AND u.UserPassword = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(clientSql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userPassword);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapClient(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String adminSql = "SELECT u.UserID, u.FirstName, u.LastName, u.Email, u.PhoneNumber, u.DateOfBirth, u.UserName, u.UserPassword, a.Salary, a.DateOfEmployement " +
                "FROM Users u JOIN AdminCinema a ON u.UserID = a.AdminID WHERE u.UserName = ? AND u.UserPassword = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(adminSql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userPassword);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAdmin(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Users getUser(int id) {
        String clientSql = "SELECT u.UserID, u.FirstName, u.LastName, u.Email, u.PhoneNumber, u.DateOfBirth, u.UserName, u.UserPassword, c.TypeID " +
                "FROM Users u JOIN Client c ON u.UserID = c.ClientID WHERE u.UserID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(clientSql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapClient(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String adminSql = "SELECT u.UserID, u.FirstName, u.LastName, u.Email, u.PhoneNumber, u.DateOfBirth, u.UserName, u.UserPassword, a.Salary, a.DateOfEmployement " +
                "FROM Users u JOIN AdminCinema a ON u.UserID = a.AdminID WHERE u.UserID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(adminSql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAdmin(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addUser(Users user) throws SQLException {
        String userSql = "INSERT INTO Users (FirstName, LastName, Email, PhoneNumber, DateOfBirth, UserName, UserPassword) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection()) {
            connection.setAutoCommit(false);
            try {
                try (PreparedStatement preparedStatement = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, user.getFirstName());
                    preparedStatement.setString(2, user.getLastName());
                    preparedStatement.setString(3, user.getEmail());
                    preparedStatement.setString(4, user.getPhoneNumber());
                    preparedStatement.setDate(5, Date.valueOf(user.getDateOfBirth()));
                    preparedStatement.setString(6, user.getUserName());
                    preparedStatement.setString(7, user.getUserPassword());
                    preparedStatement.executeUpdate();
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            user.setUserID(generatedKeys.getInt(1));
                        } else {
                            throw new SQLException("Failed to retrieve generated user ID.");
                        }
                    }
                }
                if (user instanceof Client) {
                    String clientSql = "INSERT INTO Client (ClientID, TypeID) VALUES (?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(clientSql)) {
                        preparedStatement.setInt(1, user.getUserID());
                        preparedStatement.setInt(2, ((Client) user).getTypeID());
                        preparedStatement.executeUpdate();
                    }
                } else if (user instanceof AdminCinema) {
                    String adminSql = "INSERT INTO AdminCinema (AdminID, Salary, DateOfEmployement) VALUES (?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(adminSql)) {
                        preparedStatement.setInt(1, user.getUserID());
                        preparedStatement.setDouble(2, ((AdminCinema) user).getSalary());
                        preparedStatement.setDate(
                                3,
                                Date.valueOf(((AdminCinema) user).getDateOfEmployment())
                        );
                        preparedStatement.executeUpdate();
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    @Override
    public void updateUser(Users user) {
        String userSql = "UPDATE Users SET FirstName = ?, LastName = ?, Email = ?, PhoneNumber = ?, DateOfBirth = ?, UserName = ?, UserPassword = ? WHERE UserID = ?";
        try (Connection connection = DatabaseConfig.getConnection()) {
            connection.setAutoCommit(false);
            try {
                try (PreparedStatement preparedStatement = connection.prepareStatement(userSql)) {
                    preparedStatement.setString(1, user.getFirstName());
                    preparedStatement.setString(2, user.getLastName());
                    preparedStatement.setString(3, user.getEmail());
                    preparedStatement.setString(4, user.getPhoneNumber());
                    preparedStatement.setDate(5, Date.valueOf(user.getDateOfBirth()));
                    preparedStatement.setString(6, user.getUserName());
                    preparedStatement.setString(7, user.getUserPassword());
                    preparedStatement.setInt(8, user.getUserID());
                    preparedStatement.executeUpdate();
                }
                if (user instanceof Client) {
                    String clientSql = "UPDATE Client SET TypeID = ? WHERE ClientID = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(clientSql)) {
                        preparedStatement.setInt(1, ((Client) user).getTypeID());
                        preparedStatement.setInt(2, user.getUserID());
                        preparedStatement.executeUpdate();
                    }
                } else if (user instanceof AdminCinema) {
                    String adminSql = "UPDATE AdminCinema SET Salary = ?, DateOfEmployement = ? WHERE AdminID = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(adminSql)) {
                        preparedStatement.setDouble(1, ((AdminCinema) user).getSalary());
                        preparedStatement.setDate(
                                2,
                                Date.valueOf(((AdminCinema) user).getDateOfEmployment())
                        );
                        preparedStatement.setInt(3, user.getUserID());
                        preparedStatement.executeUpdate();
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Users user) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getUserID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client mapClient(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getInt("UserID"),
                resultSet.getString("FirstName"),
                resultSet.getString("LastName"),
                resultSet.getString("Email"),
                resultSet.getString("PhoneNumber"),
                resultSet.getDate("DateOfBirth").toLocalDate(),
                resultSet.getString("UserName"),
                resultSet.getString("UserPassword"),
                resultSet.getInt("TypeID")
        );
    }

    private AdminCinema mapAdmin(ResultSet resultSet) throws SQLException {
        return new AdminCinema(
                resultSet.getInt("UserID"),
                resultSet.getString("FirstName"),
                resultSet.getString("LastName"),
                resultSet.getString("Email"),
                resultSet.getString("PhoneNumber"),
                resultSet.getDate("DateOfBirth").toLocalDate(),
                resultSet.getString("UserName"),
                resultSet.getString("UserPassword"),
                resultSet.getDouble("Salary"),
                resultSet.getDate("DateOfEmployement").toLocalDate()
        );
    }
}