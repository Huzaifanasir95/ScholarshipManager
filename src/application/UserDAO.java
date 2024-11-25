package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Authenticate user by email and password
    public UserDTO authenticateUser(String email, String password) throws Exception {
        String query = "SELECT UserID, UserName, Email, Role FROM Users WHERE Email = ? AND Password = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserDTO(
                        rs.getInt("UserID"),
                        rs.getString("UserName"),
                        rs.getString("Email"),
                        rs.getString("Role")
                );
            } else {
                throw new Exception("Invalid credentials.");
            }
        }
    }

    // Register a new student
    public boolean createStudent(String username, String email, String password) throws Exception {
        String query = "INSERT INTO Users (UserName, Email, Password, Role) VALUES (?, ?, ?, 'Student')";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            return stmt.executeUpdate() > 0;
        }
    }

    // Check if a user with the given email already exists
    public boolean checkUserExists(String email) throws Exception {
        String query = "SELECT COUNT(*) AS Count FROM Users WHERE Email = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Count") > 0;
            }
        }
        return false;
    }
}
