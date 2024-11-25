package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class ScholarshipApplyController {

    @FXML
    private TextField nameField, fatherNameField, cnicField, addressField, phoneField, emailField;
    @FXML
    private TextField universityField, degreeProgramField, cgpaField, matricMarksField, interMarksField;
    @FXML
    private TextArea achievementsField, statementOfPurposeField, extracurricularField;
    @FXML
    private DatePicker dobField;

    private String scholarshipName; // To hold the scholarship name being applied for

    // File paths for the uploaded documents
    private String intermediateDocPath = null;
    private String fscDocPath = null;
    private String ieltsDocPath = null;

    @FXML
    private void uploadIntermediateDocument() {
        intermediateDocPath = selectFile("Select Intermediate Certificate");
        System.out.println("Uploaded Intermediate Certificate: " + intermediateDocPath);
    }

    @FXML
    private void openIntermediateDocument() {
        openDocument(intermediateDocPath, "Intermediate Certificate");
    }

    @FXML
    private void uploadFscDocument() {
        fscDocPath = selectFile("Select FSC Certificate");
        System.out.println("Uploaded FSC Certificate: " + fscDocPath);
    }

    @FXML
    private void openFscDocument() {
        openDocument(fscDocPath, "FSC Certificate");
    }

    @FXML
    private void uploadIeltsDocument() {
        ieltsDocPath = selectFile("Select IELTS Certificate");
        System.out.println("Uploaded IELTS Certificate: " + ieltsDocPath);
    }

    @FXML
    private void openIeltsDocument() {
        openDocument(ieltsDocPath, "IELTS Certificate");
    }

    private String selectFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File file = fileChooser.showOpenDialog(getStage());
        return (file != null) ? file.getAbsolutePath() : null;
    }

    private void openDocument(String filePath, String documentName) {
        if (filePath == null) {
            System.out.println("No file uploaded for " + documentName);
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
        }
    }

    @FXML
    private void submitApplication() {
        try {
            // Retrieve the logged-in user's ID and email
            int userId = SessionManager.getLoggedInUserId();
            String email = SessionManager.getLoggedInUserEmail();

            // Collect input fields
            String name = nameField.getText();
            String fatherName = fatherNameField.getText();
            String cnic = cnicField.getText();
            String dob = (dobField.getValue() != null) ? dobField.getValue().toString() : null;
            String address = addressField.getText();
            String phone = phoneField.getText();
            String university = universityField.getText();
            double cgpa = Double.parseDouble(cgpaField.getText());
            double matricMarks = Double.parseDouble(matricMarksField.getText());
            double interMarks = Double.parseDouble(interMarksField.getText());
            String achievements = achievementsField.getText();
            String statementOfPurpose = statementOfPurposeField.getText();
            String extracurricular = extracurricularField.getText();

            // Fetch ScholarshipID and DegreeProgram
            int scholarshipId = DatabaseUtil.getScholarshipID(scholarshipName);
            String degreeProgram = DatabaseUtil.getScholarshipLevel(scholarshipName);

            // Save the application
            DatabaseUtil.saveApplication(
                userId, scholarshipId, degreeProgram, name, fatherName, cnic, dob, address, phone, university,
                cgpa, matricMarks, interMarks, achievements, statementOfPurpose, extracurricular,
                intermediateDocPath, fscDocPath, ieltsDocPath, email
            );

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Your application has been submitted successfully!");
            alert.showAndWait();

            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to submit application. Please try again.");
            alert.showAndWait();
        }
    }






    @FXML
    private void closeWindow() {
        getStage().close();
    }

    public void setScholarshipName(String scholarshipName) {
        this.scholarshipName = scholarshipName;
        System.out.println("Applying for: " + scholarshipName);
    }

    private Stage getStage() {
        return (Stage) nameField.getScene().getWindow();
    }
}
