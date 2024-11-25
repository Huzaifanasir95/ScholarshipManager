package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddScholarshipController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField providerField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField eligibilityField;

    @FXML
    private TextField deadlineField;

    @FXML
    private TextField countryField;

    @FXML
    private ComboBox<String> levelField;

    @FXML
    public void initialize() {
        // Populate the levelField ComboBox with options
        levelField.setItems(FXCollections.observableArrayList("MS", "BS", "PhD"));
    }

    /**
     * Handles adding a new scholarship.
     */
    @FXML
    private void addScholarship() {
        try {
            // Gather form input
            String name = nameField.getText();
            String provider = providerField.getText();
            String description = descriptionField.getText();
            String eligibility = eligibilityField.getText();
            String deadline = deadlineField.getText();
            String country = countryField.getText();
            String level = levelField.getValue();

            // Validate input
            if (name.isEmpty() || provider.isEmpty() || description.isEmpty() || eligibility.isEmpty()
                    || deadline.isEmpty() || country.isEmpty() || level == null) {
                showErrorAlert("Validation Error", "Please fill in all required fields.");
                return;
            }

            // Create Scholarship object
            Scholarship newScholarship = new Scholarship(0, name, description, eligibility, "", deadline, country, level);
            newScholarship.setProvider(provider);

            // Save to database
            DatabaseUtil.addScholarship(newScholarship);

            // Show success alert
            showInfoAlert("Success", "Scholarship added successfully!");

            // Close the form
            closeWindow();
        } catch (SQLException e) {
            showErrorAlert("Database Error", "Failed to save scholarship to the database.");
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
