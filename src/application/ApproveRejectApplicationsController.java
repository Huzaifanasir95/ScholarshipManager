package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class ApproveRejectApplicationsController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox applicationsContainer;

    /**
     * Initializes the application view.
     */
    @FXML
    public void initialize() {
        fetchApplications();
    }

    /**
     * Fetches the applications from the database and populates the UI.
     */
    private void fetchApplications() {
        try {
            List<Application> applications = DatabaseUtil.getApplications(); // Retrieve applications from DB
            populateApplications(applications);
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error", "Failed to fetch applications from the database.");
        }
    }

    /**
     * Populates the application list in the UI.
     *
     * @param applicationsToDisplay List of applications to display.
     */
    private void populateApplications(List<Application> applicationsToDisplay) {
        applicationsContainer.getChildren().clear();

        for (Application application : applicationsToDisplay) {
            VBox applicationBox = createApplicationBox(application);
            applicationsContainer.getChildren().add(applicationBox);
        }
    }

    /**
     * Creates a UI box for a single application.
     *
     * @param application The application to represent.
     * @return A VBox representing the application.
     */
    private VBox createApplicationBox(Application application) {
        VBox box = new VBox(10);
        box.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-border-radius: 5;");

        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setOnAction(event -> viewApplicationDetails(application));

        Button approveButton = new Button("Approve");
        approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        approveButton.setOnAction(event -> approveApplication(application));

        Button rejectButton = new Button("Reject");
        rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        rejectButton.setOnAction(event -> rejectApplication(application));

        box.getChildren().addAll(
            new javafx.scene.control.Label("Applicant: " + application.getFullName()),
            new javafx.scene.control.Label("CNIC: " + application.getCnic()),
            new javafx.scene.control.Label("Scholarship: " + application.getScholarshipName()),
            viewDetailsButton,
            approveButton,
            rejectButton
        );

        return box;
    }

    /**
     * Displays detailed information about the selected application.
     *
     * @param application The application to view details for.
     */
    private void viewApplicationDetails(Application application) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ApplicationViewDetails.fxml"));
            Parent root = loader.load();

            // Pass the application details to the controller
            ApplicationViewDetailsController controller = loader.getController();
            controller.setApplicationDetails(application);

            Stage stage = new Stage();
            stage.setTitle("Application Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Approves the selected application.
     *
     * @param application The application to approve.
     */
    private void approveApplication(Application application) {
        try {
            DatabaseUtil.updateApplicationStatus(application.getApplicationID(), "Approved");
            applicationsContainer.getChildren().removeIf(node ->
                node instanceof VBox && ((VBox) node).getChildren().get(0).toString().contains(application.getFullName())
            );
            showInfo("Success", "Application approved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error", "Failed to approve the application.");
        }
    }

    private void rejectApplication(Application application) {
        try {
            DatabaseUtil.updateApplicationStatus(application.getApplicationID(), "Rejected");
            applicationsContainer.getChildren().removeIf(node ->
                node instanceof VBox && ((VBox) node).getChildren().get(0).toString().contains(application.getFullName())
            );
            showInfo("Success", "Application rejected successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error", "Failed to reject the application.");
        }
    }


    /**
     * Displays an informational alert.
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an error alert.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the search functionality for filtering applications.
     */
    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase();

        try {
            List<Application> applications = DatabaseUtil.getApplications();
            List<Application> filteredApplications = applications.stream()
                .filter(app -> app.getFullName().toLowerCase().contains(query) ||
                               app.getCnic().toLowerCase().contains(query) ||
                               app.getScholarshipName().toLowerCase().contains(query))
                .toList();
            populateApplications(filteredApplications);
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error", "Failed to filter applications.");
        }
    }

    /**
     * Navigates back to the admin dashboard.
     */
    @FXML
    private void goBackToAdminDashboard() {
        try {
            Stage currentStage = (Stage) searchField.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Parent root = loader.load();

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Admin Dashboard");
            dashboardStage.setScene(new Scene(root));
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
