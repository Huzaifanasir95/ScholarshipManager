package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ManageAccountController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField visiblePasswordField;

    @FXML
    private CheckBox showPasswordCheckBox;

    private boolean isPasswordVisible = false;

    @FXML
    public void initialize() {
        int userId = SessionManager.getLoggedInUserId(); // Fetch logged-in user
        try {
            // Fetch account details
            String name = DatabaseUtil.getStudentName(userId);
            String email = DatabaseUtil.getStudentEmail(userId);
            String password = DatabaseUtil.getStudentPassword(userId);

            // Populate fields
            nameField.setText(name != null ? name : "N/A");
            emailField.setText(email != null ? email : "N/A");
            passwordField.setText(password != null ? password : "********");
            visiblePasswordField.setText(password);

        } catch (SQLException e) {
            // Show error message
            showError("Failed to load account details from the database.");
            e.printStackTrace();
        }
    }

    @FXML
    private void toggleShowPassword() {
        isPasswordVisible = !isPasswordVisible;

        if (isPasswordVisible) {
            visiblePasswordField.setText(passwordField.getText());
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
        } else {
            passwordField.setText(visiblePasswordField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);
        }
    }

    @FXML
    private void handleChangeName() {
        nameField.setEditable(true);
        nameField.requestFocus();
    }

    @FXML
    private void handleChangeEmail() {
        emailField.setEditable(true);
        emailField.requestFocus();
    }

    @FXML
    private void handleChangePassword() {
        if (isPasswordVisible) {
            visiblePasswordField.setEditable(true);
            visiblePasswordField.requestFocus();
        } else {
            passwordField.setEditable(true);
            passwordField.requestFocus();
        }
    }

    @FXML
    private void handleSaveChanges() {
        String newName = nameField.getText();
        String newEmail = emailField.getText();
        String newPassword = isPasswordVisible ? visiblePasswordField.getText() : passwordField.getText();

        // Validate inputs
        if (newName.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
            showError("All fields must be filled.");
            return;
        }

        try {
            // Update database
            DatabaseUtil.updateUserDetails(SessionManager.getLoggedInUserId(), newName, newEmail, newPassword);

            // Reset fields
            nameField.setEditable(false);
            emailField.setEditable(false);
            passwordField.setEditable(false);
            visiblePasswordField.setEditable(false);

            // Success message
            showInfo("Account details updated successfully!");
        } catch (SQLException e) {
            showError("Failed to update account details.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        nameField.getScene().getWindow().hide();
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            // Close the current Manage Account window
            Stage currentStage = (Stage) nameField.getScene().getWindow();
            currentStage.close();

            // Open AdminDashboard.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Parent root = loader.load();

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Admin Dashboard");
            dashboardStage.setScene(new Scene(root));
            dashboardStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to navigate back to the dashboard.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
