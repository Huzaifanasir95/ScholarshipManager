package application;

public class ApplicationStatusDTO {
    private String scholarshipName;
    private String status;

    public ApplicationStatusDTO(String scholarshipName, String status) {
        this.scholarshipName = scholarshipName;
        this.status = status;
    }

    public String getScholarshipName() {
        return scholarshipName;
    }

    public String getStatus() {
        return status;
    }
}
