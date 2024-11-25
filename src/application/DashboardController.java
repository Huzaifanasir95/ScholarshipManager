package application;

import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DashboardController {

    @FXML
    private VBox sidePanel;

    @FXML
    private AnchorPane manageAccountPane;

    @FXML
    private Label nameField;

    @FXML
    private Label emailField;

    @FXML
    private Label phoneField;

    private boolean isPanelVisible = true; // Tracks visibility of the side panel
    

    @FXML
    private VBox notificationsBox;

    @FXML
    private Label scholarshipsAvailableLabel;

    @FXML
    private Label applicationsLabel;

    @FXML
    private Label acceptedApplicationsLabel;

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label studentNameLabel;

    @FXML
    private Label studentEmailLabel;

    @FXML
    private Label studentPhoneLabel;


    @FXML
    private Label welcomeStudentLabel;

    
    @FXML
    public void initialize() {
    	 String studentUsername = SessionManager.getLoggedInUsername(); // Fetch username from SessionManager
         
         // Set the welcome label with the student's username
         welcomeStudentLabel.setText("Welcome, " + studentUsername + "!");
        // Load student details and statistics
//        loadStudentDetails();  // Sets the student's name and email
        try {
            loadQuickInfo();  // Updates the Quick Info section
            loadStudentStatistics();  // Loads statistics for the main dashboard
//            loadRecentNotifications();  // Displays recent notifications for the student
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load student-specific information, such as name, email, and phone.
     */


    
    /**
     * Load student-specific quick info such as the number of scholarships, applications, and accepted applications.
     */
    private void loadQuickInfo() {
        try {
            // Get the logged-in user's ID
            int userId = SessionManager.getLoggedInUserId();

            // Fetch statistics for the student
            int scholarshipsAvailable = DatabaseUtil.getScholarshipCount();
            int applicationsCount = DatabaseUtil.getStudentApplicationCount(userId);
            int acceptedApplicationsCount = DatabaseUtil.getStudentAcceptedApplicationCount(userId);

            // Set the statistics in the UI
            scholarshipsAvailableLabel.setText("Scholarships Available: " + scholarshipsAvailable);
            applicationsLabel.setText("Your Applications: " + applicationsCount);
            acceptedApplicationsLabel.setText("Accepted Applications: " + acceptedApplicationsCount);

        } catch (Exception e) {
            e.printStackTrace();
            scholarshipsAvailableLabel.setText("Scholarships Available: Error");
            applicationsLabel.setText("Your Applications: Error");
            acceptedApplicationsLabel.setText("Accepted Applications: Error");
        }
    }


    // Toggle the side panel visibility
    


    /**
     * Load student statistics such as the number of scholarships, applications, and accepted applications.
     */
    private void loadStudentStatistics() throws Exception {
        int userId = SessionManager.getLoggedInUserId();

        // Fetch statistics
        int scholarshipsAvailable = DatabaseUtil.getScholarshipCount();
        int applicationsCount = DatabaseUtil.getStudentApplicationCount(userId);
        int acceptedApplications = DatabaseUtil.getStudentAcceptedApplicationCount(userId);

        // Set statistics in labels
        scholarshipsAvailableLabel.setText(String.valueOf(scholarshipsAvailable));
        applicationsLabel.setText(String.valueOf(applicationsCount));
        acceptedApplicationsLabel.setText(String.valueOf(acceptedApplications));
    }

    /**
     * Load and display recent notifications for the logged-in student.
     */
    private void loadRecentNotifications() throws Exception {
        int userId = SessionManager.getLoggedInUserId();
        List<String> notifications = DatabaseUtil.getRecentNotifications(userId);

        notificationsBox.getChildren().clear();
        if (notifications.isEmpty()) {
            Label noNotificationsLabel = new Label("No recent notifications.");
            noNotificationsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
            notificationsBox.getChildren().add(noNotificationsLabel);
        } else {
            for (String notification : notifications) {
                Label notificationLabel = new Label("• " + notification);
                notificationLabel.setStyle("-fx-font-size: 14px;");
                notificationsBox.getChildren().add(notificationLabel);
            }
        }
    }
    
    @FXML
    private void toggleSidePanel() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.3), sidePanel);
        if (isPanelVisible) {
            transition.setByX(-200); // Slide out
            isPanelVisible = false;
        } else {
            transition.setByX(200); // Slide back in
            isPanelVisible = true;
        }
        transition.play();
    }

    // Show the Manage Account pane
    @FXML
    private void showManageAccount() {
        try {
            // Load the ManageAccount.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageAccount.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = new Stage();
            stage.setTitle("Manage Account");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hide the Manage Account pane (this is now properly defined)
    @FXML
    private void hideManageAccount() {
        if (manageAccountPane != null) {
            manageAccountPane.setVisible(false);
            manageAccountPane.setManaged(false);
        } else {
            System.out.println("Manage Account pane is null or not initialized.");
        }
    }

    // Handle logout functionality
    @FXML
    private void handleLogout() {
        try {
            // Close the current dashboard window
            Stage currentStage = (Stage) sidePanel.getScene().getWindow();
            currentStage.close();

            // Load the Login.fxml screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Placeholder methods for side panel buttons
    @FXML
    private void handleScholarshipOptions() {
        try {
            // Load Scholarship.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scholarship.fxml"));
            Parent root = loader.load();

            // Close the current dashboard
            Stage currentStage = (Stage) sidePanel.getScene().getWindow();
            currentStage.close();

            // Open the Scholarship window
            Stage scholarshipStage = new Stage();
            scholarshipStage.setTitle("Scholarship Options");
            scholarshipStage.setScene(new Scene(root));
            scholarshipStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleTrackApplicationStatus() {
        try {
            // Load TrackApplicationStatus.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrackApplicationStatus.fxml"));
            Parent root = loader.load();

            // Close the current dashboard
            Stage currentStage = (Stage) sidePanel.getScene().getWindow();
            currentStage.close();

            // Open the Track Application Status window
            Stage trackStatusStage = new Stage();
            trackStatusStage.setTitle("Track Application Status");
            trackStatusStage.setScene(new Scene(root));
            trackStatusStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNotifications() {
        try {
            // Load Notifications.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Notifications.fxml"));
            Parent root = loader.load();

            // Close the current dashboard
            Stage currentStage = (Stage) sidePanel.getScene().getWindow();
            currentStage.close();

            // Open the Notifications window
            Stage notificationsStage = new Stage();
            notificationsStage.setTitle("Notifications");
            notificationsStage.setScene(new Scene(root));
            notificationsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleApplicationHistory() {
        try {
            // Load ApplicationHistory.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ApplicationHistory.fxml"));
            Parent root = loader.load();

            // Close the current dashboard
            Stage currentStage = (Stage) sidePanel.getScene().getWindow();
            currentStage.close();

            // Open the Application History window
            Stage historyStage = new Stage();
            historyStage.setTitle("Application History");
            historyStage.setScene(new Scene(root));
            historyStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
}
