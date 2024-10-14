package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class userServices {
    public static void registerUser(String username, String email) throws SQLException {
        String sql = "INSERT INTO Users (User_name, User_email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        }
    }
    public static boolean isUserRegistered(String email) throws SQLException {
        String sql = "SELECT User_id FROM Users WHERE User_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();  // If a result exists, the user is registered
            }
        }
    }

}


