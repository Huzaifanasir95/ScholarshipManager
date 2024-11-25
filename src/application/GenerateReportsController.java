package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenerateReportsController {

    @FXML
    private ComboBox<String> reportTypeComboBox;

    @FXML
    private ScrollPane reportScrollPane;

    @FXML
    private VBox reportContainer;

    private final List<String> currentReportData = new ArrayList<>();

    /**
     * Initializes the controller and populates the ComboBox with report types.
     */
    @FXML
    public void initialize() {
        reportTypeComboBox.setItems(FXCollections.observableArrayList(
                "Scholarships Overview",
                "Student Participation",
                "System Usage Statistics"
        ));
    }

    /**
     * Handles the Generate Report button click event.
     */
    @FXML
    private void generateReport() {
        reportContainer.getChildren().clear();
        currentReportData.clear();

        String selectedReport = reportTypeComboBox.getValue();

        if (selectedReport == null) {
            reportContainer.getChildren().add(new Label("Please select a report type."));
            return;
        }

        switch (selectedReport) {
            case "Scholarships Overview" -> generateScholarshipsOverview();
            case "Student Participation" -> generateStudentParticipation();
            case "System Usage Statistics" -> generateSystemUsageStatistics();
            default -> reportContainer.getChildren().add(new Label("Unknown report type selected."));
        }
    }

    /**
     * Generates the Scholarships Overview Report.
     */
    private void generateScholarshipsOverview() {
        try {
            currentReportData.add("Scholarships Overview Report");
            List<String> scholarships = DatabaseUtil.getScholarshipsOverview();

            for (String scholarship : scholarships) {
                currentReportData.add(scholarship);
            }
        } catch (SQLException e) {
            currentReportData.add("Failed to generate Scholarships Overview report.");
            e.printStackTrace();
        }

        displayReport();
    }

    /**
     * Generates the Student Participation Report.
     */
    private void generateStudentParticipation() {
        try {
            currentReportData.add("Student Participation Report");
            List<String> studentParticipation = DatabaseUtil.getStudentParticipation();

            for (String participation : studentParticipation) {
                currentReportData.add(participation);
            }
        } catch (SQLException e) {
            currentReportData.add("Failed to generate Student Participation report.");
            e.printStackTrace();
        }

        displayReport();
    }

    /**
     * Generates the System Usage Statistics Report.
     */
    private void generateSystemUsageStatistics() {
        try {
            currentReportData.add("System Usage Statistics Report");
            List<String> systemStats = DatabaseUtil.getSystemUsageStatistics();

            for (String stat : systemStats) {
                currentReportData.add(stat);
            }
        } catch (SQLException e) {
            currentReportData.add("Failed to generate System Usage Statistics report.");
            e.printStackTrace();
        }

        displayReport();
    }

    /**
     * Displays the current report in the UI.
     */
    private void displayReport() {
        reportContainer.getChildren().clear();
        for (String line : currentReportData) {
            reportContainer.getChildren().add(new Label(line));
        }
    }

    /**
     * Handles the Download Report button click event.
     */
    @FXML
    private void downloadReport() {
        if (currentReportData.isEmpty()) {
            reportContainer.getChildren().add(new Label("No report to download. Please generate a report first."));
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showSaveDialog(reportScrollPane.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                for (String line : currentReportData) {
                    writer.write(line + System.lineSeparator());
                }
                writer.flush();
                reportContainer.getChildren().add(new Label("Report successfully downloaded to: " + file.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
                reportContainer.getChildren().add(new Label("Failed to download the report. Please try again."));
            }
        }
    }

    /**
     * Handles the Back to Dashboard button click event.
     */
    @FXML
    private void goBackToDashboard() {
        try {
            Stage currentStage = (Stage) reportScrollPane.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Parent root = loader.load();

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Admin Dashboard");
            dashboardStage.setScene(new Scene(root));
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
