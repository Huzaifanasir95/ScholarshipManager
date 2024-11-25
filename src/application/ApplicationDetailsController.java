package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ApplicationDetailsController {

    @FXML
    private Label scholarshipNameLabel;

    @FXML
    private Label submissionDateLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label reviewerCommentsLabel;

    /**
     * Set details of the scholarship application.
     *
     * @param scholarshipName   The name of the scholarship.
     * @param submissionDate    The date the application was submitted.
     * @param status            The current status of the application.
     * @param reviewerComments  The comments from the reviewer (if any).
     */
    public void setApplicationDetails(String scholarshipName, String submissionDate, String status, String reviewerComments) {
        scholarshipNameLabel.setText(scholarshipName);
        submissionDateLabel.setText("Submission Date: " + submissionDate);
        statusLabel.setText("Status: " + status);
        reviewerCommentsLabel.setText("Reviewer Comments: " + (reviewerComments != null ? reviewerComments : "No comments available"));
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) scholarshipNameLabel.getScene().getWindow();
        stage.close();
    }
}
