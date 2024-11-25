package application;

public class ApplicationHistoryDTO {
    private String scholarshipName;
    private String submissionDate;

    public ApplicationHistoryDTO(String scholarshipName, String submissionDate) {
        this.scholarshipName = scholarshipName;
        this.submissionDate = submissionDate;
    }

    public String getScholarshipName() {
        return scholarshipName;
    }

    public void setScholarshipName(String scholarshipName) {
        this.scholarshipName = scholarshipName;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }
}
