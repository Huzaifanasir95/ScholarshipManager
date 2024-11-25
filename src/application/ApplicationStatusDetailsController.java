package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ApplicationStatusDetailsController {

    @FXML
    private Label scholarshipNameLabel, statusLabel, remarksLabel;

    public void setScholarshipDetails(String scholarshipName, String status, String remarks) {
        scholarshipNameLabel.setText(scholarshipName);
        statusLabel.setText(status);
        remarksLabel.setText(remarks);
    }
}
