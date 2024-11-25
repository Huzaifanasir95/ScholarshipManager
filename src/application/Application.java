package application;

public class Application {
    private int applicationID;
    private String fullName;
    private String fatherName;
    private String cnic;
    private String dateOfBirth;
    private String address;
    private String phone;
    private String email;
    private String university;
    private String degreeProgram;
    private double cgpa;
    private double matricMarks;
    private double interMarks;
    private String academicAchievements;
    private String statementOfPurpose;
    private String extracurricular;
    private String scholarshipName;
    private String status;

    // Constructor
    public Application(int applicationID, String fullName, String fatherName, String cnic, String dateOfBirth,
                       String address, String phone, String email, String university, String degreeProgram,
                       double cgpa, double matricMarks, double interMarks, String academicAchievements,
                       String statementOfPurpose, String extracurricular, String scholarshipName, String status) {
        this.applicationID = applicationID;
        this.fullName = fullName;
        this.fatherName = fatherName;
        this.cnic = cnic;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.university = university;
        this.degreeProgram = degreeProgram;
        this.cgpa = cgpa;
        this.matricMarks = matricMarks;
        this.interMarks = interMarks;
        this.academicAchievements = academicAchievements;
        this.statementOfPurpose = statementOfPurpose;
        this.extracurricular = extracurricular;
        this.scholarshipName = scholarshipName;
        this.status = status;
    }

    // Getters and Setters
    public int getApplicationID() { return applicationID; }
    public void setApplicationID(int applicationID) { this.applicationID = applicationID; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }
    public String getCnic() { return cnic; }
    public void setCnic(String cnic) { this.cnic = cnic; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }
    public String getDegreeProgram() { return degreeProgram; }
    public void setDegreeProgram(String degreeProgram) { this.degreeProgram = degreeProgram; }
    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }
    public double getMatricMarks() { return matricMarks; }
    public void setMatricMarks(double matricMarks) { this.matricMarks = matricMarks; }
    public double getInterMarks() { return interMarks; }
    public void setInterMarks(double interMarks) { this.interMarks = interMarks; }
    public String getAcademicAchievements() { return academicAchievements; }
    public void setAcademicAchievements(String academicAchievements) { this.academicAchievements = academicAchievements; }
    public String getStatementOfPurpose() { return statementOfPurpose; }
    public void setStatementOfPurpose(String statementOfPurpose) { this.statementOfPurpose = statementOfPurpose; }
    public String getExtracurricular() { return extracurricular; }
    public void setExtracurricular(String extracurricular) { this.extracurricular = extracurricular; }
    public String getScholarshipName() { return scholarshipName; }
    public void setScholarshipName(String scholarshipName) { this.scholarshipName = scholarshipName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
