package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class NotificationsController {

    @FXML
    private VBox newScholarshipsBox;

    @FXML
    private VBox applicationResponsesBox;

    @FXML
    public void initialize() {
        try {
            // Populate New Scholarships Section
            populateNewScholarships();

            // Populate Application Responses Section
            populateApplicationResponses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateNewScholarships() throws Exception {
        List<Notification> newScholarships = DatabaseUtil.getNewScholarshipNotifications();

        if (newScholarships.isEmpty()) {
            Label label = new Label("No new scholarships available.");
            label.setStyle("-fx-font-size: 14px; -fx-font-style: italic; -fx-text-fill: gray;");
            newScholarshipsBox.getChildren().add(label);
        } else {
            for (Notification notification : newScholarships) {
                Label label = new Label("• " + notification.getMessage());
                label.setStyle("-fx-font-size: 14px;");
                newScholarshipsBox.getChildren().add(label);
            }
        }
    }

    private void populateApplicationResponses() throws Exception {
        int userId = SessionManager.getLoggedInUserId();
        List<Notification> responses = DatabaseUtil.getApplicationResponseNotifications(userId);

        if (responses.isEmpty()) {
            Label label = new Label("No application responses at the moment.");
            label.setStyle("-fx-font-size: 14px; -fx-font-style: italic; -fx-text-fill: gray;");
            applicationResponsesBox.getChildren().add(label);
        } else {
            for (Notification notification : responses) {
                Label label = new Label("• " + notification.getMessage());
                label.setStyle("-fx-font-size: 14px;");
                applicationResponsesBox.getChildren().add(label);
            }
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            // Close current Notifications window
            Stage currentStage = (Stage) newScholarshipsBox.getScene().getWindow();
            currentStage.close();

            // Open Dashboard
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
