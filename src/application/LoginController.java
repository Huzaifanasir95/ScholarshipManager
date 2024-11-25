package application;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox loginForm;

    @FXML
    private VBox signupForm;

    private boolean showingLogin = true;

    private final UserService userService = new UserService(); // UserService instance

    @FXML
    public void toggleForm() {
        RotateTransition rotateOut = new RotateTransition(Duration.seconds(0.5), stackPane);
        rotateOut.setFromAngle(0);
        rotateOut.setToAngle(90);
        rotateOut.setOnFinished(e -> {
            loginForm.setVisible(!showingLogin);
            signupForm.setVisible(showingLogin);
            showingLogin = !showingLogin;

            RotateTransition rotateIn = new RotateTransition(Duration.seconds(0.5), stackPane);
            rotateIn.setFromAngle(90);
            rotateIn.setToAngle(360);
            rotateIn.play();
        });

        rotateOut.play();
    }

    @FXML
    public void openDashboard(ActionEvent event) {
        try {
            // Retrieve input fields
            TextField emailField = (TextField) loginForm.lookup("#loginEmailField");
            PasswordField passwordField = (PasswordField) loginForm.lookup("#loginPasswordField");

            String email = emailField.getText();
            String password = passwordField.getText();

            // Authenticate user via UserService
            UserDTO user = userService.login(email, password);

            if (user != null) {
                // Store session data
                SessionManager.setLoggedInUserId(user.getUserId());
                SessionManager.setLoggedInUserRole(user.getRole());
                SessionManager.setLoggedInUsername(user.getUsername());

                // Redirect based on role
                if ("Admin".equalsIgnoreCase(user.getRole())) {
                    openAdminDashboard(event);
                } else if ("Student".equalsIgnoreCase(user.getRole())) {
                    openStudentDashboard(event);
                } else {
                    showError("Invalid role. Access denied.");
                }
            } else {
                showError("Invalid credentials. Please try again.");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void handleSignup(ActionEvent event) {
        try {
            // Retrieve input fields
            TextField usernameField = (TextField) signupForm.lookup("#signupUsernameField");
            TextField emailField = (TextField) signupForm.lookup("#signupEmailField");
            PasswordField passwordField = (PasswordField) signupForm.lookup("#signupPasswordField");

            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            // Attempt signup via UserService
            boolean isSuccess = userService.signup(username, email, password);

            if (isSuccess) {
                showSuccess("Signup successful! You can now log in.");
                toggleForm(); // Switch back to login form
            } else {
                showError("Signup failed. Please try again.");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void openAdminDashboard(ActionEvent event) {
        try {
            Parent adminDashboardRoot = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(adminDashboardRoot));
            stage.setTitle("Admin Dashboard");
            stage.show();
        } catch (Exception e) {
            showError("Failed to open Admin Dashboard: " + e.getMessage());
        }
    }

    public void openStudentDashboard(ActionEvent event) {
        try {
            Parent studentDashboardRoot = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(studentDashboardRoot));
            stage.setTitle("Student Dashboard");
            stage.show();
        } catch (Exception e) {
            showError("Failed to open Student Dashboard: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void openGuestView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScholarshipGuest.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Guest Scholarships");
            stage.show();
        } catch (Exception e) {
            showError("Failed to open Guest View: " + e.getMessage());
        }
    }

}
