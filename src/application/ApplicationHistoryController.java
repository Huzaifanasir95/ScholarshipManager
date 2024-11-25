package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationHistoryController {

    @FXML
    private TextField searchBar;

    @FXML
    private GridPane applicationGrid;

    private List<ApplicationHistoryDTO> applicationHistory;

    @FXML
    public void initialize() {
        // Fetch application history for the logged-in user
        try {
            int userId = SessionManager.getLoggedInUserId();
            applicationHistory = DatabaseUtil.getApplicationHistory(userId);
            populateApplicationGrid(applicationHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateApplicationGrid(List<ApplicationHistoryDTO> applications) {
        applicationGrid.getChildren().clear();

        for (int i = 0; i < applications.size(); i++) {
            ApplicationHistoryDTO application = applications.get(i);

            VBox applicationBox = new VBox(10);
            applicationBox.setStyle("-fx-border-color: #1E2A38; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #F9F9F9;");

            Label scholarshipName = new Label(application.getScholarshipName());
            scholarshipName.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            Label submissionDate = new Label("Submitted on: " + application.getSubmissionDate());

            Button viewDetailsButton = new Button("View Details");
            viewDetailsButton.setOnAction(e -> viewApplicationDetails(application.getScholarshipName()));

            Button feedbackButton = new Button("Provide Feedback");
            feedbackButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 10;");
            feedbackButton.setOnAction(e -> provideFeedback(application.getScholarshipName()));

            applicationBox.getChildren().addAll(scholarshipName, submissionDate, viewDetailsButton, feedbackButton);
            applicationGrid.add(applicationBox, i % 2, i / 2);
        }
    }

    private void viewApplicationDetails(String scholarshipName) {
        try {
            int userId = SessionManager.getLoggedInUserId();
            ApplicationDetailsDTO details = DatabaseUtil.getApplicationDetails(userId, scholarshipName);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ApplicationDetails.fxml"));
            Parent root = loader.load();

            ApplicationDetailsController controller = loader.getController();
            controller.setApplicationDetails(
                scholarshipName, 
                details.getSubmissionDate(), 
                details.getStatus(), 
                details.getReviewerComments()
            );

            Stage stage = new Stage();
            stage.setTitle("Application Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void provideFeedback(String scholarshipName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProvideFeedback.fxml"));
            Parent root = loader.load();

            ProvideFeedbackController controller = loader.getController();
            controller.setScholarshipName(scholarshipName);

            Stage stage = new Stage();
            stage.setTitle("Provide Feedback");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String query = searchBar.getText().toLowerCase();
        List<ApplicationHistoryDTO> filteredApplications = applicationHistory.stream()
                .filter(app -> app.getScholarshipName().toLowerCase().contains(query))
                .collect(Collectors.toList());

        populateApplicationGrid(filteredApplications);
    }

    @FXML
    private void goBackToDashboard() {
        try {
            Stage currentStage = (Stage) searchBar.getScene().getWindow();
            currentStage.close();

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
