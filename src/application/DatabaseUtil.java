package application;

//import java.security.Timestamp;
import java.sql.Timestamp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


public class DatabaseUtil {

    private static final String URL = "jdbc:sqlserver://PAVILION\\\\SQLEXPRESS01:65096;databaseName=Scholarship;integratedSecurity=true;trustServerCertificate=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
    public static UserDTO validateLogin(String email, String password) throws Exception {
        String query = "SELECT UserID, UserName, Role FROM Users WHERE Email = ? AND Password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserDTO(
                        rs.getInt("UserID"),
                        rs.getString("UserName"),
                        email, // Use the email passed as the parameter
                        rs.getString("Role")
                    );
                }
            }
        }
        return null; // Return null if no matching user is found
    }


    public static boolean registerUser(String username, String email, String password, String role) throws Exception {
        String query = "INSERT INTO Users (UserName, Email, Password, Role) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);

            return stmt.executeUpdate() > 0; // Returns true if the row is inserted successfully
        }
    }


    // Fetch all scholarships
    public static List<Scholarship> fetchAllScholarships() throws Exception {
        List<Scholarship> scholarships = new ArrayList<>();
        String query = """
                SELECT ScholarshipID, ScholarshipName, Description, EligibilityCriteria, 
                       ApplicationRequirements, FORMAT(ApplicationDeadline, 'yyyy-MM-dd') AS LastDate, 
                       Country, Level
                FROM Scholarships
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                scholarships.add(new Scholarship(
                        rs.getInt("ScholarshipID"),
                        rs.getString("ScholarshipName"),
                        rs.getString("Description"),
                        rs.getString("EligibilityCriteria"),
                        rs.getString("ApplicationRequirements"),
                        rs.getString("LastDate"),
                        rs.getString("Country"),
                        rs.getString("Level")
                ));
            }
        }
        return scholarships;
    }

    public static String getScholarshipLevel(String scholarshipName) throws Exception {
        String query = "SELECT Level FROM Scholarships WHERE ScholarshipName = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, scholarshipName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Level");
                } else {
                    throw new Exception("Scholarship level not found for: " + scholarshipName);
                }
            }
        }
    }

    public static int getScholarshipID(String scholarshipName) throws Exception {
        String query = "SELECT ScholarshipID FROM Scholarships WHERE ScholarshipName = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, scholarshipName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ScholarshipID");
                } else {
                    throw new Exception("Scholarship not found: " + scholarshipName);
                }
            }
        }
    }

    
    public static void saveApplication(
            int userId, int scholarshipId, String degreeProgram, String name, String fatherName, String cnic,
            String dob, String address, String phone, String university, double cgpa,
            double matricMarks, double interMarks, String achievements, String statementOfPurpose,
            String extracurricular, String intermediateDocPath, String fscDocPath, String ieltsDocPath,
            String email) throws Exception {

        String query = """
            INSERT INTO Applications (
                UserID, ScholarshipID, FullName, FatherName, CNIC, DateOfBirth, Address, Phone,
                University, DegreeProgram, CGPA, MatricMarks, InterMarks, AcademicAchievements,
                StatementOfPurpose, Extracurricular, IntermediateCertificatePath, FSCCertificatePath,
                IELTSCertificatePath, SubmissionDate, Email, Status
            ) VALUES (
                ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, 'Submitted'
            )
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);                      // UserID
            stmt.setInt(2, scholarshipId);               // ScholarshipID
            stmt.setString(3, name);                     // FullName
            stmt.setString(4, fatherName);               // FatherName
            stmt.setString(5, cnic);                     // CNIC
            stmt.setString(6, dob);                      // DateOfBirth
            stmt.setString(7, address);                  // Address
            stmt.setString(8, phone);                    // Phone
            stmt.setString(9, university);               // University
            stmt.setString(10, degreeProgram);           // DegreeProgram
            stmt.setDouble(11, cgpa);                    // CGPA
            stmt.setDouble(12, matricMarks);             // MatricMarks
            stmt.setDouble(13, interMarks);              // InterMarks
            stmt.setString(14, achievements);            // AcademicAchievements
            stmt.setString(15, statementOfPurpose);      // StatementOfPurpose
            stmt.setString(16, extracurricular);         // Extracurricular
            stmt.setString(17, intermediateDocPath);     // IntermediateCertificatePath
            stmt.setString(18, fscDocPath);              // FSCCertificatePath
            stmt.setString(19, ieltsDocPath);            // IELTSCertificatePath
            stmt.setString(20, email);                   // Email

            stmt.executeUpdate();
        }
    }


    public static List<ApplicationStatusDTO> getApplicationsForUser(int userId) throws Exception {
        String query = """
            SELECT s.ScholarshipName, a.Status
            FROM Applications a
            JOIN Scholarships s ON a.ScholarshipID = s.ScholarshipID
            WHERE a.UserID = ?
            """;

        List<ApplicationStatusDTO> applications = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    applications.add(new ApplicationStatusDTO(
                            rs.getString("ScholarshipName"),
                            rs.getString("Status")
                    ));
                }
            }
        }
        return applications;
    }

    public static String getApplicationRemarks(int userId, String scholarshipName) throws Exception {
        String query = """
            SELECT ReviewerComments
            FROM Applications a
            JOIN Scholarships s ON a.ScholarshipID = s.ScholarshipID
            WHERE a.UserID = ? AND s.ScholarshipName = ?
            """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setString(2, scholarshipName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("ReviewerComments");
                }
            }
        }
        return "No remarks available.";
    }

    public static List<Notification> getNewScholarshipNotifications() throws Exception {
        String query = """
            SELECT ScholarshipName 
            FROM Scholarships 
            WHERE ApplicationDeadline > GETDATE() 
            ORDER BY ApplicationDeadline ASC
        """;

        List<Notification> notifications = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String scholarshipName = rs.getString("ScholarshipName");
                String message = scholarshipName + " - Apply Now!";
                notifications.add(new Notification(scholarshipName, message, "New Scholarship"));
            }
        }

        return notifications;
    }


    public static List<Notification> getApplicationResponseNotifications(int userId) throws Exception {
        String query = """
            SELECT s.ScholarshipName, a.Status 
            FROM Applications a
            JOIN Scholarships s ON a.ScholarshipID = s.ScholarshipID
            WHERE a.UserID = ?
            ORDER BY a.SubmissionDate DESC
        """;

        List<Notification> notifications = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String scholarshipName = rs.getString("ScholarshipName");
                    String status = rs.getString("Status");
                    String message = scholarshipName + " - " + status;
                    notifications.add(new Notification(scholarshipName, message, "Application Response"));
                }
            }
        }

        return notifications;
    }

    public static List<ApplicationHistoryDTO> getApplicationHistory(int userId) throws Exception {
        String query = """
            SELECT s.ScholarshipName, FORMAT(a.SubmissionDate, 'yyyy-MM-dd') AS SubmissionDate
            FROM Applications a
            JOIN Scholarships s ON a.ScholarshipID = s.ScholarshipID
            WHERE a.UserID = ?
            ORDER BY a.SubmissionDate DESC
        """;

        List<ApplicationHistoryDTO> applicationHistory = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String scholarshipName = rs.getString("ScholarshipName");
                    String submissionDate = rs.getString("SubmissionDate");
                    applicationHistory.add(new ApplicationHistoryDTO(scholarshipName, submissionDate));
                }
            }
        }

        return applicationHistory;
    }

    public static ApplicationDetailsDTO getApplicationDetails(int userId, String scholarshipName) throws Exception {
        String query = """
            SELECT a.SubmissionDate, a.Status, a.ReviewerComments
            FROM Applications a
            JOIN Scholarships s ON a.ScholarshipID = s.ScholarshipID
            WHERE a.UserID = ? AND s.ScholarshipName = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setString(2, scholarshipName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String submissionDate = rs.getString("SubmissionDate");
                    String status = rs.getString("Status");
                    String reviewerComments = rs.getString("ReviewerComments");

                    return new ApplicationDetailsDTO(submissionDate, status, reviewerComments);
                }
            }
        }

        throw new Exception("No application details found for the specified scholarship.");
    }

    public static void saveFeedback(Feedback feedback) throws SQLException {
        String query = """
            INSERT INTO Feedback (
                UserID, ScholarshipName, FeedbackType, FeedbackText, Rating, SubmittedAt
            ) VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, feedback.getUserId());
            stmt.setString(2, feedback.getScholarshipName());
            stmt.setString(3, "General");
            stmt.setString(4, feedback.getReview() != null ? feedback.getReview() : "");
            stmt.setDouble(5, feedback.getRating());
            stmt.setTimestamp(6, Timestamp.valueOf(feedback.getSubmittedAt()));

            stmt.executeUpdate();
        }
    }

 // Fetch user name
    public static String getStudentName(int userId) throws SQLException {
        String query = "SELECT UserName FROM Users WHERE UserID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("UserName");
                }
            }
        }
        return null;
    }

    // Fetch user email
    public static String getStudentEmail(int userId) throws SQLException {
        String query = "SELECT Email FROM Users WHERE UserID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Email");
                }
            }
        }
        return null;
    }

    // Fetch user password (hashing recommended, but we'll fetch raw for now)
    public static String getStudentPassword(int userId) throws SQLException {
        String query = "SELECT Password FROM Users WHERE UserID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Password");
                }
            }
        }
        return null;
    }


    public static void updateStudentDetails(int userId, String name, String email, String password) throws SQLException {
        String query = "UPDATE Users SET UserName = ?, Email = ?, Password = ? WHERE UserID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setInt(4, userId);
            stmt.executeUpdate();
        }
    }
    public static void updateUserDetails(int userId, String newName, String newEmail, String newPassword) throws SQLException {
        String query = "UPDATE Users SET UserName = ?, Email = ?, Password = ? WHERE UserID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setString(2, newEmail);
            stmt.setString(3, newPassword); // For security, hash the password here
            stmt.setInt(4, userId);
            stmt.executeUpdate();
        }
    }

    public static int getScholarshipCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM Scholarships";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public static int getStudentApplicationCount(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Applications WHERE UserID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public static int getStudentAcceptedApplicationCount(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Applications WHERE UserID = ? AND Status = 'Approved'";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public static List<String> getRecentNotifications(int userId) throws SQLException {
        String query = "SELECT Message FROM Notifications WHERE UserID = ? ORDER BY SentAt DESC LIMIT 3";
        List<String> notifications = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notifications.add(rs.getString("Message"));
                }
            }
        }
        return notifications;
    }

    
    public static List<Scholarship> getAllScholarships() throws SQLException {
        String query = "SELECT * FROM Scholarships";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            List<Scholarship> scholarships = new ArrayList<>();
            while (rs.next()) {
                scholarships.add(new Scholarship(
                        rs.getInt("ScholarshipID"),
                        rs.getString("ScholarshipName"),
                        rs.getString("Description"),
                        rs.getString("EligibilityCriteria"),
                        rs.getString("ApplicationRequirements"),
                        rs.getString("ApplicationDeadline"),
                        rs.getString("Country"),
                        rs.getString("Level")
                ));
            }
            return scholarships;
        }
    }

    // Delete a scholarship by ID
    public static void deleteScholarship(int scholarshipId) throws SQLException {
        String query = "DELETE FROM Scholarships WHERE ScholarshipID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, scholarshipId);
            stmt.executeUpdate();
        }
    }
    
    public static void addScholarship(Scholarship scholarship) throws SQLException {
        String query = """
            INSERT INTO Scholarships (ScholarshipName, ProviderName, Description, EligibilityCriteria, ApplicationDeadline, 
                                      ApplicationRequirements, Level, Country, ViewCount, ApplicationCount)
            VALUES (?, ?, ?, ?, ?, '', ?, ?, 0, 0)
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, scholarship.getName());
            stmt.setString(2, scholarship.getProvider());
            stmt.setString(3, scholarship.getDescription());
            stmt.setString(4, scholarship.getEligibilityCriteria());
            stmt.setString(5, scholarship.getLastDate());
            stmt.setString(6, scholarship.getAcademicLevel());
            stmt.setString(7, scholarship.getCountry());

            stmt.executeUpdate();
        }
    }

    public static List<Application> getApplications() throws SQLException {
        List<Application> applications = new ArrayList<>();

        String query = """
            SELECT 
                a.ApplicationID,
                a.FullName,
                a.FatherName,
                a.CNIC,
                a.DateOfBirth,
                a.Address,
                a.Phone,
                a.Email,
                a.University,
                a.DegreeProgram,
                a.CGPA,
                a.MatricMarks,
                a.InterMarks,
                a.AcademicAchievements,
                a.StatementOfPurpose,
                a.Extracurricular,
                s.ScholarshipName,
                a.Status
            FROM 
                Applications a
            JOIN 
                Scholarships s 
            ON 
                a.ScholarshipID = s.ScholarshipID
            WHERE 
                a.Status NOT IN ('Approved', 'Rejected')
            """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Application app = new Application(
                    rs.getInt("ApplicationID"),
                    rs.getString("FullName"),
                    rs.getString("FatherName"),
                    rs.getString("CNIC"),
                    rs.getString("DateOfBirth"),
                    rs.getString("Address"),
                    rs.getString("Phone"),
                    rs.getString("Email"),
                    rs.getString("University"),
                    rs.getString("DegreeProgram"),
                    rs.getDouble("CGPA"),
                    rs.getDouble("MatricMarks"),
                    rs.getDouble("InterMarks"),
                    rs.getString("AcademicAchievements"),
                    rs.getString("StatementOfPurpose"),
                    rs.getString("Extracurricular"),
                    rs.getString("ScholarshipName"),
                    rs.getString("Status")
                );

                applications.add(app);
            }
        }

        return applications;
    }

    public static void updateApplicationStatus(int applicationID, String status) throws SQLException {
        String query = "UPDATE Applications SET Status = ? WHERE ApplicationID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set query parameters
            stmt.setString(1, status);
            stmt.setInt(2, applicationID);

            // Execute update
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Application status updated successfully.");
            } else {
                System.out.println("No application found with the given ID.");
            }
        }
    }

    public static List<String> getScholarshipsOverview() throws SQLException {
        List<String> scholarships = new ArrayList<>();
        String query = "SELECT ScholarshipName, ApplicationCount FROM Scholarships";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                scholarships.add(rs.getString("ScholarshipName") + " - Applications: " + rs.getInt("ApplicationCount"));
            }
        }

        return scholarships;
    }

    public static List<String> getStudentParticipation() throws SQLException {
        List<String> participation = new ArrayList<>();
        String query = """
            SELECT u.UserName, s.ScholarshipName
            FROM Applications a
            JOIN Users u ON a.UserID = u.UserID
            JOIN Scholarships s ON a.ScholarshipID = s.ScholarshipID
            """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                participation.add(rs.getString("UserName") + " applied for " + rs.getString("ScholarshipName"));
            }
        }

        return participation;
    }

    public static List<String> getSystemUsageStatistics() throws SQLException {
        List<String> stats = new ArrayList<>();
        String query = """
            SELECT
                (SELECT COUNT(*) FROM Users) AS TotalUsers,
                (SELECT SUM(ViewCount) FROM Scholarships) AS TotalViews,
                (SELECT COUNT(*) FROM Applications) AS TotalApplications
            """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                stats.add("Total Users: " + rs.getInt("TotalUsers"));
                stats.add("Total Scholarship Views: " + rs.getInt("TotalViews"));
                stats.add("Total Applications: " + rs.getInt("TotalApplications"));
            }
        }

        return stats;
    }

    public static int getTotalScholarships() throws SQLException {
        String query = "SELECT COUNT(*) AS TotalScholarships FROM Scholarships";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("TotalScholarships");
            }
        }
        return 0;
    }

    // Get the total number of applications
    public static int getTotalApplications() throws SQLException {
        String query = "SELECT COUNT(*) AS TotalApplications FROM Applications";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("TotalApplications");
            }
        }
        return 0;
    }

    // Get the total number of approved applications
    public static int getApprovedApplications() throws SQLException {
        String query = "SELECT COUNT(*) AS ApprovedApplications FROM Applications WHERE Status = 'Approved'";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("ApprovedApplications");
            }
        }
        return 0;
    }

    // Get the latest N logs
    public static String[] getLatestLogs(int count) throws SQLException {
        String query = "SELECT TOP (?) Action FROM Logs ORDER BY ActionTime DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, count);
            try (ResultSet rs = stmt.executeQuery()) {
                String[] logs = new String[count];
                int i = 0;
                while (rs.next() && i < count) {
                    logs[i++] = rs.getString("Action");
                }
                return logs;
            }
        }
    }
    
 // Get the total number of pending applications
    public static int getPendingApplications() throws SQLException {
        String query = "SELECT COUNT(*) AS PendingApplications FROM Applications WHERE Status IN ('Submitted', 'Under Review')";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("PendingApplications");
            }
        }
        return 0;
    }

    public static void addBookmark(int userId, int scholarshipId) throws SQLException {
        String sql = "INSERT INTO Bookmarks (UserId, ScholarshipId, CreatedAt) VALUES (?, ?, GETDATE())";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, scholarshipId);
            stmt.executeUpdate();
        }
    }

    // Method to fetch all bookmarks for a user
    public static List<Bookmark> getUserBookmarks(int userId) throws SQLException {
        String sql = "SELECT b.BookmarkId, b.UserId, b.ScholarshipId, s.ScholarshipName, b.CreatedAt " +
                     "FROM Bookmarks b " +
                     "JOIN Scholarships s ON b.ScholarshipId = s.ScholarshipID " +
                     "WHERE b.UserId = ?";
        List<Bookmark> bookmarks = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookmarks.add(new Bookmark(
                        rs.getInt("BookmarkId"),
                        rs.getInt("UserId"),
                        rs.getInt("ScholarshipId"),
                        rs.getString("ScholarshipName"),
                        rs.getString("CreatedAt")
                    ));
                }
            }
        }
        return bookmarks;
    }

    // Method to delete a bookmark
    public static void deleteBookmark(int bookmarkId) throws SQLException {
        String sql = "DELETE FROM Bookmarks WHERE BookmarkId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookmarkId);
            stmt.executeUpdate();
        }
    }

}
