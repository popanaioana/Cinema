package org.example.cinema.repository.db;

import org.example.cinema.domain.AdminCinema;
import org.example.cinema.domain.Client;
import org.example.cinema.domain.Users;
import org.example.cinema.repository.interfaces.IUsersRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDBRepository implements IUsersRepository {
    private String connectionString = "jdbc:sqlserver://localhost:1435;" + "databaseName=CinemaDB;" + "user=cinema_app;" + "password=CinemaApp@2025!;" + "encrypt=true;" + "trustServerCertificate=true;";

    @Override
    public List<Users> getUsers() {
        List<Users> users = new ArrayList<>();
        String sql = "select * from Users inner join Clients on Users.ClientID = Client.ClientID";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Client client = new Client(
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
                users.add(client);
                };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "select * from Users inner join AdminCinema on Users.ClientID = AdminCinema.AdminCinemaID";
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                AdminCinema adminCinema = new AdminCinema(
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
                users.add(adminCinema);
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Users getUser(String username, String userPassword) {
        String sql = "select * from Users inner join Client on Users.UserID = Client.ClientID where UserName = ? and UserPassword = ?";
        Users user = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userPassword);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new Client(resultSet.getInt("UserID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Email"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getDate("DateOfBirth").toLocalDate(),
                            resultSet.getString("UserName"),
                            resultSet.getString("UserPassword"),
                            resultSet.getInt("TypeID"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "select * from Users inner join AdminCinema on Users.UserID = AdminCinema.AdminID where UserName = ? and UserPassword = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userPassword);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new AdminCinema(resultSet.getInt("UserID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Email"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getDate("DateOfBirth").toLocalDate(),
                            resultSet.getString("UserName"),
                            resultSet.getString("UserPassword"),
                            resultSet.getDouble("Salary"),
                            resultSet.getDate("DateOfEmployement").toLocalDate());
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Users getUser(int id) {
        String sql = "select * from Users inner join Client on Users.UserID = Client.ClientID where UserID = ?";
        Users user = null;
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new Client(resultSet.getInt("UserID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Email"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getDate("DateOfBirth").toLocalDate(),
                            resultSet.getString("UserName"),
                            resultSet.getString("UserPassword"),
                            resultSet.getInt("TypeID"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "select * from Users inner join AdminCinema on Users.UserID = AdminCinema.AdminID where UserID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null) {
                    user = new AdminCinema(resultSet.getInt("UserID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Email"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getDate("DateOfBirth").toLocalDate(),
                            resultSet.getString("UserName"),
                            resultSet.getString("UserPassword"),
                            resultSet.getDouble("Salary"),
                            resultSet.getDate("DateOfEmployement").toLocalDate());
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void addUser(Users user) throws SQLException {
        String sql= "INSERT INTO Users (FirstName, LastName, Email, PhoneNumber, DateOfBirth, UserName, UserPassword) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
                        int generatedId = generatedKeys.getInt(1);
                        user.setUserID(generatedId);
                    }
                }
            }
            if (user instanceof Client) {
                String sqlClient = "INSERT INTO Client (ClientID, TypeID) VALUES (?, ?)";
                try (PreparedStatement preparedStatementClient = connection.prepareStatement(sqlClient)) {
                    preparedStatementClient.setInt(1, user.getUserID());
                    preparedStatementClient.setInt(2, ((Client) user).getTypeID());
                    preparedStatementClient.executeUpdate();
                }
            } else if (user instanceof AdminCinema) {
                String sqlAdmin = "INSERT INTO AdminCinema (AdminID, Salary, DateOfEmployement) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatementAdmin = connection.prepareStatement(sqlAdmin)) {
                    preparedStatementAdmin.setInt(1, user.getUserID());
                    preparedStatementAdmin.setDouble(2, ((AdminCinema) user).getSalary());
                    preparedStatementAdmin.setDate(3, Date.valueOf(((AdminCinema) user).getDateOfEmployment()));
                    preparedStatementAdmin.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void updateUser(Users user) {
        String sql = "update Users set FirstName = ?, LastName = ?, Email = ?, PhoneNumber = ?, DateOfBirth = ?, UserName = ?, UserPassword = ? where UserName = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setDate(5, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setString(6, user.getUserName());
            preparedStatement.setString(7, user.getUserPassword());
            preparedStatement.setString(8, user.getUserName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user instanceof Client) {
            sql = "update Client set TypeID = ? where ClientID = ?";
            try (Connection connection = DriverManager.getConnection(connectionString);
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                preparedStatement.setInt(1, ((Client)user).getTypeID());
                preparedStatement.setInt(2, user.getUserID());
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (user instanceof AdminCinema) {
            sql = "update AdminCinema set Salary = ?, DateOfEmployement = ? where AdminID = ?";
            try (Connection connection = DriverManager.getConnection(connectionString);
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                preparedStatement.setDouble(1, ((AdminCinema)user).getSalary());
                preparedStatement.setDate(2, Date.valueOf(((AdminCinema)user).getDateOfEmployment()));
                preparedStatement.setInt(3, user.getUserID());
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteUser(Users user) {
        String sql = "delete from Users where UserID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, user.getUserID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}