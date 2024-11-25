package application;

public class ApplicationDetailsDTO {
    private String submissionDate;
    private String status;
    private String reviewerComments;

    public ApplicationDetailsDTO(String submissionDate, String status, String reviewerComments) {
        this.submissionDate = submissionDate;
        this.status = status;
        this.reviewerComments = reviewerComments;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public String getStatus() {
        return status;
    }

    public String getReviewerComments() {
        return reviewerComments;
    }
}
