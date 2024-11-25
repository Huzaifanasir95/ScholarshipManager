package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ScholarshipDetailsController {

    @FXML
    private Label scholarshipName;

    @FXML
    private Label scholarshipDescription;

    @FXML
    private Label scholarshipCriteria;

    public void setScholarshipDetails(String[] scholarship) {
        scholarshipName.setText(scholarship[0]);
        scholarshipDescription.setText(scholarship[1]);
        scholarshipCriteria.setText("Eligibility Criteria: \n1. Minimum GPA: 3.5\n2. Excellent Leadership Skills\n3. Strong Academic Record");
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) scholarshipName.getScene().getWindow();
        stage.close();
    }
}
