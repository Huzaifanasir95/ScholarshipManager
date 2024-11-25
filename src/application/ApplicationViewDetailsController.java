package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ApplicationViewDetailsController {

    @FXML private Label nameLabel;
    @FXML private Label fatherNameLabel;
    @FXML private Label cnicLabel;
    @FXML private Label dobLabel;
    @FXML private Label addressLabel;
    @FXML private Label phoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label universityLabel;
    @FXML private Label degreeProgramLabel;
    @FXML private Label cgpaLabel;
    @FXML private Label matricMarksLabel;
    @FXML private Label interMarksLabel;
    @FXML private Label achievementsLabel;
    @FXML private Label sopLabel;
    @FXML private Label extracurricularLabel;
    @FXML private Label scholarshipLabel;

    public void setApplicationDetails(Application application) {
        nameLabel.setText(application.getFullName());
        fatherNameLabel.setText(application.getFatherName());
        cnicLabel.setText(application.getCnic());
        dobLabel.setText(application.getDateOfBirth());
        addressLabel.setText(application.getAddress());
        phoneLabel.setText(application.getPhone());
        emailLabel.setText(application.getEmail());
        universityLabel.setText(application.getUniversity());
        degreeProgramLabel.setText(application.getDegreeProgram());
        cgpaLabel.setText(String.valueOf(application.getCgpa()));
        matricMarksLabel.setText(String.valueOf(application.getMatricMarks()));
        interMarksLabel.setText(String.valueOf(application.getInterMarks()));
        achievementsLabel.setText(application.getAcademicAchievements());
        sopLabel.setText(application.getStatementOfPurpose());
        extracurricularLabel.setText(application.getExtracurricular());
        scholarshipLabel.setText(application.getScholarshipName());
    }
}
