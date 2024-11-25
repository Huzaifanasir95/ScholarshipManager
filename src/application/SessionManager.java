package application;

public class SessionManager {

    private static int loggedInUserId;   // Store UserID of the logged-in user
    private static String loggedInUserRole; // Store the role (e.g., Student, Admin)
    private static String loggedInUsername; // Store the username (optional)
    private static String loggedInUserEmail; // Store the email of the logged-in user

    // Getter and Setter for UserID
    public static void setLoggedInUserId(int userId) {
        loggedInUserId = userId;
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    // Getter and Setter for User Role
    public static void setLoggedInUserRole(String role) {
        loggedInUserRole = role;
    }

    public static String getLoggedInUserRole() {
        return loggedInUserRole;
    }

    // Getter and Setter for Username
    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    // Getter and Setter for User Email
    public static void setLoggedInUserEmail(String email) {
        loggedInUserEmail = email;
    }

    public static String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }

    // Method to clear session (e.g., on logout)
    public static void clearSession() {
        loggedInUserId = 0;
        loggedInUserRole = null;
        loggedInUsername = null;
        loggedInUserEmail = null;
    }
}
