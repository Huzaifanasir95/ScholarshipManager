package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class TrackApplicationStatusController {

    @FXML
    private VBox statusContainer;

    @FXML
    public void initialize() {
        // Populate the status container with real application statuses for the logged-in student
        try {
            populateApplicationStatuses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the status container with application statuses for the logged-in student.
     */
    private void populateApplicationStatuses() throws Exception {
        int userId = SessionManager.getLoggedInUserId(); // Get the logged-in user's ID
        List<ApplicationStatusDTO> applications = DatabaseUtil.getApplicationsForUser(userId);

        // Clear any existing content
        statusContainer.getChildren().clear();

        // Iterate through the statuses and create UI elements dynamically
        for (ApplicationStatusDTO application : applications) {
            VBox applicationBox = createApplicationBox(application.getScholarshipName(), application.getStatus());
            statusContainer.getChildren().add(applicationBox);
        }

        if (applications.isEmpty()) {
            Label noApplicationsLabel = new Label("You have not applied for any scholarships.");
            noApplicationsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
            statusContainer.getChildren().add(noApplicationsLabel);
        }
    }

    /**
     * Creates a styled VBox containing details about a scholarship application.
     *
     * @param scholarshipName The name of the scholarship.
     * @param status          The status of the application.
     * @return A VBox containing the application details.
     */
    private VBox createApplicationBox(String scholarshipName, String status) {
        VBox applicationBox = new VBox(10); // Spacing of 10 between elements
        applicationBox.setStyle("-fx-border-color: #1E90FF; -fx-border-width: 1; -fx-border-radius: 5; "
                + "-fx-padding: 10; -fx-background-color: #F0F8FF; -fx-background-radius: 5;");

        // Scholarship name
        Label scholarshipNameLabel = new Label(scholarshipName);
        scholarshipNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1E2A38;");

        // Scholarship status
        Label statusLabel = new Label("Status: " + status);
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");

        // View Details button
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-border-radius: 5;");
        viewDetailsButton.setOnAction(e -> showDetails(scholarshipName, status));

        applicationBox.getChildren().addAll(scholarshipNameLabel, statusLabel, viewDetailsButton);

        return applicationBox;
    }

    /**
     * Displays the details of a specific scholarship application in a new window.
     *
     * @param scholarshipName The name of the scholarship.
     * @param status          The status of the application.
     */
    private void showDetails(String scholarshipName, String status) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ApplicationStatusDetails.fxml"));
            Parent root = loader.load();

            // Pass data to ApplicationStatusDetailsController
            ApplicationStatusDetailsController controller = loader.getController();
            String remarks = DatabaseUtil.getApplicationRemarks(SessionManager.getLoggedInUserId(), scholarshipName); // Fetch remarks dynamically
            controller.setScholarshipDetails(scholarshipName, status, remarks);

            // Set up the stage
            Stage stage = new Stage();
            stage.setTitle("Application Status Details");
            stage.setScene(new Scene(root, 400, 300)); // Fixed dimensions for better usability
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the "Back to Dashboard" button click event.
     */
    @FXML
    private void handleBackToDashboard() {
        try {
            // Close the current Track Application Status window
            Stage currentStage = (Stage) statusContainer.getScene().getWindow();
            currentStage.close();

            // Load the Dashboard scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Dashboard");
            dashboardStage.setScene(new Scene(root));
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
