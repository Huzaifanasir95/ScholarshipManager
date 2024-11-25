package application;

public class Scholarship {
    private int id;
    private String name;
    private String description;
    private String eligibilityCriteria;
    private String applicationRequirements;
    private String lastDate;
    private String country;
    private String academicLevel;
    private String provider;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }


    public Scholarship(int id, String name, String description, String eligibilityCriteria,
                       String applicationRequirements, String lastDate, String country, String academicLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eligibilityCriteria = eligibilityCriteria;
        this.applicationRequirements = applicationRequirements;
        this.lastDate = lastDate;
        this.country = country;
        this.academicLevel = academicLevel;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getEligibilityCriteria() { return eligibilityCriteria; }
    public void setEligibilityCriteria(String eligibilityCriteria) { this.eligibilityCriteria = eligibilityCriteria; }
    public String getApplicationRequirements() { return applicationRequirements; }
    public void setApplicationRequirements(String applicationRequirements) { this.applicationRequirements = applicationRequirements; }
    public String getLastDate() { return lastDate; }
    public void setLastDate(String lastDate) { this.lastDate = lastDate; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getAcademicLevel() { return academicLevel; }
    public void setAcademicLevel(String academicLevel) { this.academicLevel = academicLevel; }
}
