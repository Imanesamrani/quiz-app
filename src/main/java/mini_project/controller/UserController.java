package mini_project.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import  mini_project.database.DatabaseConnection;

import mini_project.model.User;
import mini_project.utils.PasswordHasher;

public class UserController {

    public static final int CreateUser(User user) {
        // hadi sql commande li radi tzid user l user table
        String sql = "INSERT INTO users (firstname, lastname, email, password, role) VALUES (?,?,?,?,?)";
        try (
                // establish database connection
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ) {

                
                // hana knhaxiw password 
                String hashedPassword = PasswordHasher.hashPassword(user.getPassword());
                
                // had lines k remplasiw `?` li f `sql commande` b data li brina nzido 3la 7sab
                // index daylha 
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setString(5, user.getRole());

            // had line katbda execution dyal request
            statement.executeUpdate();
            System.out.println("User created successfully");


            // Retrieve the generated quiz ID
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create user");
        }

        return -1;
    }

    public static final void DeleteUser(int userId) {
        String sql = "DELETE from users WHERE id = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setInt(1, userId);

            statement.executeUpdate();
            System.out.println("User Deleted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete user");
        }

    }

    public static final User getUserById(int userId) {
        String sql = "SELECT * FROM users where id = ?";
        try(
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new User(
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("role"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static final List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users";
        try(
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                users.add(new User(
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("role")));
            }

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static final List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * users where role = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, role);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                users.add(new User(
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("role")));
            }
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static final void UpdateUser(int id, User user) {
        String sql = "UPDATE users SET firstname = ?, lastname = ?, email = ?, password = ?, role = ? WHERE id = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            // Set values for the placeholders in the SQL query
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.setInt(6, id); // The ID of the user to update
    
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("No user found with the given ID.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update user.");
        }
    }
  
    public static final User login(String email, String password){
        String sql = "SELECT * FROM users WHERE email = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            
            statement.setString(1, email);
    
            // Execute query
            ResultSet result = statement.executeQuery(); // user: {email: moham..... , pass: 878*93%$6393}

            if (result.next()) {
                String hashedPassword = result.getString("password");
    
                // Hana knverifiw password
                if (PasswordHasher.checkPassword(password, hashedPassword)) {
                    
                    return new User(
                            result.getString("firstname"),
                            result.getString("lastname"),
                            result.getString("email"),
                            hashedPassword,
                            result.getString("role"));
                } else {
                    System.out.println("Invalid password.");
                    // throw new PasswordUserException("Invalid Password");
                }
            } else {
                System.out.println("User not found.");
                // throw new EmailUserException("Invalid Email");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        // }catch(EmailUserException emailExcp){
        //     emailExcp.getMessage();
        // }
        // catch(PasswordUserException passwordExcp){
        //     passwordExcp.getMessage();
        }
        return null;
    }
    
}
