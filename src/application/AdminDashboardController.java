package application;

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

import java.sql.SQLException;

public class AdminDashboardController {

    @FXML
    private VBox sidePanel;

    @FXML
    private VBox mainContent;

    @FXML
    private Label totalScholarshipsLabel;

    @FXML
    private Label totalApplicationsLabel;

    @FXML
    private Label approvedApplicationsLabel;

    @FXML
    private Label announcement1;

    @FXML
    private Label announcement2;

    @FXML
    private Label announcement3;

    @FXML
    private Label scholarshipsAvailableLabel;

    @FXML
    private Label pendingApplicationsLabel;

    private boolean isPanelVisible = true;
    @FXML
    private Label welcomeLabel;

    public void initialize() {
        // Fetch and set data from the database
        loadDashboardData();
        loadQuickInfo();
        loadAnnouncements();
        String adminUsername = SessionManager.getLoggedInUsername(); // Assumes SessionManager is implemented
        
        // Set the welcome label with the admin's username
        welcomeLabel.setText("Welcome, " + adminUsername + "!");

    }

    /**
     * Loads the dashboard data (e.g., total scholarships, total applications, etc.).
     */
    private void loadDashboardData() {
        try {
            // Fetch counts from the database
            int totalScholarships = DatabaseUtil.getTotalScholarships();
            int totalApplications = DatabaseUtil.getTotalApplications();
            int approvedApplications = DatabaseUtil.getApprovedApplications();

            // Set the values in the labels
            totalScholarshipsLabel.setText(String.valueOf(totalScholarships));
            totalApplicationsLabel.setText(String.valueOf(totalApplications));
            approvedApplicationsLabel.setText(String.valueOf(approvedApplications));
        } catch (SQLException e) {
            e.printStackTrace();
            // Use placeholder data in case of an error
            totalScholarshipsLabel.setText("N/A");
            totalApplicationsLabel.setText("N/A");
            approvedApplicationsLabel.setText("N/A");
        }
    }

    /**
     * Loads the Quick Info section in the side panel.
     */
    private void loadQuickInfo() {
        try {
            // Fetch data for Quick Info
            int scholarshipsAvailable = DatabaseUtil.getTotalScholarships();
            int pendingApplications = DatabaseUtil.getPendingApplications();

            // Set the values in the labels
            scholarshipsAvailableLabel.setText(String.valueOf(scholarshipsAvailable));
            pendingApplicationsLabel.setText(String.valueOf(pendingApplications));
        } catch (SQLException e) {
            e.printStackTrace();
            // Use placeholder data in case of an error
            scholarshipsAvailableLabel.setText("N/A");
            pendingApplicationsLabel.setText("N/A");
        }
    }

    /**
     * Loads the latest announcements from the `Logs` table.
     */
    private void loadAnnouncements() {
        try {
            // Fetch the latest three log entries from the database
            String[] latestLogs = DatabaseUtil.getLatestLogs(3);

            // Populate the announcement labels
            if (latestLogs.length > 0) announcement1.setText("1. " + latestLogs[0]);
            if (latestLogs.length > 1) announcement2.setText("2. " + latestLogs[1]);
            if (latestLogs.length > 2) announcement3.setText("3. " + latestLogs[2]);
        } catch (SQLException e) {
            e.printStackTrace();
            // Use placeholder announcements in case of an error
            announcement1.setText("1. Unable to fetch announcements.");
            announcement2.setText("2. Please check your connection.");
            announcement3.setText("3. Contact the system administrator.");
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

    @FXML
    private void handleManageScholarships() {
        navigateTo("ManageScholarships.fxml", "Manage Scholarships");
    }

    @FXML
    private void handleApproveRejectApplications() {
        navigateTo("ApproveRejectApplications.fxml", "Approve/Reject Applications");
    }

    @FXML
    private void handleGenerateReports() {
        navigateTo("GenerateReports.fxml", "Generate Reports");
    }

    @FXML
    private void showManageAccount() {
        navigateTo("ManageAccount.fxml", "Manage Account");
    }

    @FXML
    private void handleLogout() {
        navigateTo("Login.fxml", "Login");
    }

    /**
     * Navigates to a specified FXML file and opens a new window.
     *
     * @param fxmlFile The FXML file to load.
     * @param title    The title of the new window.
     */
    private void navigateTo(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Close the current window
            Stage currentStage = (Stage) sidePanel.getScene().getWindow();
            currentStage.close();

            // Open the new window
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
