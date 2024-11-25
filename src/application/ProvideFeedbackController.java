package application;

import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ProvideFeedbackController {

    @FXML
    private Label scholarshipNameLabel;

    @FXML
    private Slider ratingSlider;

    @FXML
    private TextArea reviewField;

    private String scholarshipName; // Context: Scholarship name

    /**
     * Dynamically set the scholarship name and update the label.
     *
     * @param scholarshipName The scholarship name for the feedback
     */
    public void setScholarshipName(String scholarshipName) {
        this.scholarshipName = scholarshipName;
        scholarshipNameLabel.setText("Provide Feedback for: " + scholarshipName);
    }

    /**
     * Submits the feedback, validates input, and saves it to the database.
     */
    @FXML
    private void submitFeedback() {
        try {
            // Retrieve inputs
            double rating = ratingSlider.getValue();
            String review = reviewField.getText().trim();
            int userId = SessionManager.getLoggedInUserId(); // Retrieve the logged-in user's ID
            LocalDateTime submittedAt = LocalDateTime.now();

            // Validate rating and review (optional for review)
            if (rating < 1 || rating > 5) {
                showAlert("Validation Error", "Rating must be between 1 and 5.");
                return;
            }

            // Create Feedback object
            Feedback feedback = new Feedback(userId, scholarshipName, rating, review, submittedAt);

            // Save feedback to the database
            DatabaseUtil.saveFeedback(feedback);

            // Success Alert
            showAlert("Feedback Submitted", "Thank you for your feedback!\n" +
                    "Rating: " + rating + "\nReview: " + (review.isEmpty() ? "No review provided." : review));
            
            // Close the feedback window
            closeWindow();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while submitting your feedback. Please try again.");
        }
    }

    /**
     * Cancels the feedback submission and closes the window.
     */
    @FXML
    private void cancelFeedback() {
        closeWindow();
    }

    /**
     * Closes the feedback window.
     */
    private void closeWindow() {
        Stage stage = (Stage) scholarshipNameLabel.getScene().getWindow();
        stage.close();
    }

    /**
     * Utility method to show an alert.
     *
     * @param title   Title of the alert
     * @param message Message content of the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
