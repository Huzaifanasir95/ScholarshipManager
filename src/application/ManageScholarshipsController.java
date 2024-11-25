package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ManageScholarshipsController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox scholarshipsContainer;

    /**
     * Initializes the controller and populates scholarships from the database.
     */
    @FXML
    public void initialize() {
        try {
            populateScholarshipsContainer(DatabaseUtil.getAllScholarships());
        } catch (Exception e) {
            showErrorAlert("Initialization Error", "Failed to load scholarships from the database.");
            e.printStackTrace();
        }
    }

    /**
     * Handles searching for scholarships based on the search field input.
     */
    @FXML
    private void handleSearch() {
        try {
            String query = searchField.getText().toLowerCase();
            List<Scholarship> scholarships = DatabaseUtil.getAllScholarships();
            List<Scholarship> filteredScholarships = scholarships.stream()
                    .filter(scholarship -> scholarship.getName().toLowerCase().contains(query)
                            || scholarship.getDescription().toLowerCase().contains(query)
                            || scholarship.getEligibilityCriteria().toLowerCase().contains(query))
                    .collect(Collectors.toList());
            populateScholarshipsContainer(filteredScholarships);
        } catch (Exception e) {
            showErrorAlert("Search Error", "Failed to search scholarships.");
            e.printStackTrace();
        }
    }

    /**
     * Populates the scholarships container with scholarship details.
     */
    private void populateScholarshipsContainer(List<Scholarship> scholarships) {
        scholarshipsContainer.getChildren().clear();

        for (Scholarship scholarship : scholarships) {
            VBox scholarshipBox = createScholarshipBox(scholarship);
            scholarshipsContainer.getChildren().add(scholarshipBox);
        }
    }

    /**
     * Creates a VBox representation of a scholarship.
     */
    private VBox createScholarshipBox(Scholarship scholarship) {
        VBox box = new VBox(5);
        box.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-border-radius: 5; -fx-background-radius: 5;");

        Text name = new Text(scholarship.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Text description = new Text(scholarship.getDescription());
        description.setWrappingWidth(600);

        // Delete Button
        javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Delete");
        deleteButton.setStyle("-fx-background-color: #FF4444; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> deleteScholarship(scholarship));

        box.getChildren().addAll(name, description, deleteButton);
        return box;
    }

    /**
     * Deletes a scholarship from the database and refreshes the container.
     */
    private void deleteScholarship(Scholarship scholarship) {
        try {
            DatabaseUtil.deleteScholarship(scholarship.getId());
            handleSearch(); // Refresh the list after deletion
            showInfoAlert("Delete Success", "Scholarship deleted successfully.");
        } catch (Exception e) {
            showErrorAlert("Delete Error", "Failed to delete the scholarship.");
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the Add Scholarship page.
     */
    @FXML
    private void addNewScholarship() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddScholarship.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Scholarship");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showErrorAlert("Navigation Error", "Failed to open Add Scholarship form.");
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the Admin Dashboard.
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
            showErrorAlert("Navigation Error", "Failed to return to Admin Dashboard.");
            e.printStackTrace();
        }
    }

    /**
     * Displays an error alert with the specified title and message.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an information alert with the specified title and message.
     */
    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
