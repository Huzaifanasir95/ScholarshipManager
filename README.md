Scholarship Management System
Overview
The Scholarship Management System is a feature-rich Java-based application designed to simplify and streamline the management of scholarships for administrators and students. It serves as a comprehensive platform where administrators can manage scholarships, process applications, and generate reports, while students can apply for scholarships, track their application status, and receive notifications about new opportunities. Additionally, a guest user feature allows visitors to explore available scholarships without requiring registration.

Built using JavaFX for the user interface and integrated with a robust backend utilizing a relational database, the system provides a seamless and user-friendly experience for all stakeholders.

Features
For Students
Login and Dashboard Access: Students can log in to their personalized dashboard to access all available features.
Scholarship Application: Apply for scholarships through an intuitive interface.
Track Application Status: Monitor the status of submitted applications, including feedback from administrators.
Notifications: Receive timely updates about new scholarships, application results, and other important events.
Feedback: Provide feedback on scholarships and rate them.
Bookmark Scholarships: Save favorite scholarships for later access.
For Administrators
Dashboard Access: Administrators can view insights into total scholarships, applications, and pending tasks.
Manage Scholarships: Create, edit, and delete scholarships with ease.
Approve/Reject Applications: Process student applications and provide detailed feedback.
Generate Reports: Create comprehensive reports about system usage, student participation, and scholarship statistics.
Announcements: Publish announcements about deadlines and new scholarships.
For Guests
Explore Scholarships: Guests can browse all available scholarships without logging in or signing up.
Read Scholarship Details: Access detailed descriptions and eligibility criteria for scholarships.
Technologies Used
Frontend
JavaFX: For creating the graphical user interface.
FXML: For designing layouts and views.
Backend
Java (Core and Advanced): Business logic and application functionality.
JDBC: For database connectivity.
Singleton and DAO Patterns: For maintaining a structured and scalable codebase.
Database
Microsoft SQL Server: A relational database used to store user, scholarship, application, and notification data.
Schema includes tables for:
Users
Scholarships
Applications
Notifications
Feedback
Bookmarks
Logs
Build and Deployment
Maven: For project build automation.
GitHub Actions: Configured CI/CD pipelines for building and deploying the application.
Deployment Target: Windows-based server or virtual machine.
Database Schema
Tables
CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    UserName VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Role VARCHAR(255) NOT NULL CHECK (Role IN ('Student', 'Admin'))
);



CREATE TABLE Scholarships (
    ScholarshipID INT PRIMARY KEY IDENTITY(1,1),
    ScholarshipName VARCHAR(255) NOT NULL,
    ProviderName VARCHAR(255) NOT NULL,
    Description TEXT NOT NULL,
    EligibilityCriteria TEXT,
    ApplicationDeadline DATETIME NOT NULL,
    ApplicationRequirements TEXT,
    Level VARCHAR(255) NOT NULL CHECK (Level IN ('BS', 'MS', 'PhD')),
    Country VARCHAR(255) NOT NULL,
    ViewCount INT DEFAULT 0,
    ApplicationCount INT DEFAULT 0
);



CREATE TABLE Applications (
    ApplicationID INT PRIMARY KEY IDENTITY(1,1),
    ScholarshipID INT NOT NULL,
    UserID INT NOT NULL,
    FullName VARCHAR(255) NOT NULL,
    FatherName VARCHAR(255),
    Phone VARCHAR(255),
    Address VARCHAR(255),
    DateOfBirth DATETIME,
    University VARCHAR(255),
    DegreeProgram VARCHAR(255) NOT NULL CHECK (DegreeProgram IN ('BS', 'MS', 'PhD')),
    CGPA FLOAT,
    InterMarks FLOAT,
    MatricMarks FLOAT,
    AcademicAchievements TEXT,
    StatementOfPurpose TEXT,
    Extracurricular TEXT,
    CNIC VARCHAR(255) NOT NULL,
    Status VARCHAR(255) NOT NULL CHECK (Status IN ('Submitted', 'Under Review', 'Approved', 'Rejected')),
    IsApproved Bit DEFAULT 0,
    SubmissionDate DATETIME NOT NULL,
    ReviewDate DATETIME,
    StatusUpdateDate DATETIME,
    ReviewerComments TEXT,
    FOREIGN KEY (ScholarshipID) REFERENCES Scholarships(ScholarshipID) ON DELETE CASCADE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);


CREATE TABLE Notifications (
    NotificationID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    Event TEXT NOT NULL,
    Message TEXT NOT NULL,
    SentAt DATETIME NOT NULL,
    IsRead Bit DEFAULT 0,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);


CREATE TABLE Feedback (
    FeedbackID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    FeedbackType VARCHAR(255) NOT NULL,
    FeedbackText TEXT NOT NULL,
    SubmittedAt DATETIME NOT NULL,
    AdminResponse TEXT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);

-- Add ScholarshipName column
ALTER TABLE Feedback
ADD ScholarshipName VARCHAR(255);

-- Add Rating column
ALTER TABLE Feedback
ADD Rating FLOAT CHECK (Rating BETWEEN 1 AND 5); -- Ensure rating is between 1 and 5


CREATE TABLE Documentation (
    DocumentationID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    FileName VARCHAR(255) NOT NULL,
    FilePath VARCHAR(255) NOT NULL,
    ActionTime DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);


CREATE TABLE Logs (
    LogID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    Action VARCHAR(255) NOT NULL,
    ActionTime DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);
CREATE TABLE Bookmarks (
    BookmarkId INT PRIMARY KEY IDENTITY(1,1),
    UserId INT NOT NULL,
    ScholarshipId INT NOT NULL,
    CreatedAt DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE,
    FOREIGN KEY (ScholarshipId) REFERENCES Scholarships(ScholarshipId) ON DELETE CASCADE
);
Application Workflow
Student Workflow
Login: Students log in using their credentials.
Dashboard Access:
View available scholarships.
Apply for scholarships.
Track application statuses and receive feedback.
Notifications: Receive updates about new scholarships or application results.
Feedback: Provide feedback and rate scholarships after applying.
Bookmarking: Save scholarships for later access.
Admin Workflow
Login: Administrators log in using their credentials.
Dashboard Access:
View insights into scholarships, applications, and pending tasks.
Manage Scholarships: Add, update, or delete scholarships.
Process Applications: Approve or reject student applications with feedback.
Generate Reports: Create detailed reports for analysis and record-keeping.
Announcements: Publish announcements visible to all users.
Guest Workflow
Browse Scholarships: Guests can explore available scholarships.
View Details: Access detailed information about each scholarship.
Design Patterns
GRASP Patterns
Controller: Controllers like AdminDashboardController, ScholarshipController, and LoginController manage interactions between views and the backend.
Creator: Classes like UserDAO and ScholarshipDAO are responsible for creating objects related to their domain.
Information Expert: Classes like DatabaseUtil encapsulate database operations to centralize expertise.
High Cohesion and Low Coupling: The application maintains a modular design, ensuring maintainability and scalability.
GoF Patterns
Singleton:
SessionManager: Manages user session data for logged-in users.
DatabaseUtil: Ensures a single database connection instance.
DAO (Data Access Object):
UserDAO, ScholarshipDAO, ApplicationDAO: Encapsulate database interactions for specific entities.
Observer:
Notifications and updates are pushed to users when new scholarships or updates are added.
Factory:
Used implicitly in DAOs to create objects dynamically based on query results.
User Interface
Admin Dashboard
A clean interface displaying:
Total scholarships, applications, and approved applications.
Announcements and system updates.
Quick access buttons for managing scholarships, applications, and reports.
Student Dashboard
Accessible features include:
Scholarship browsing and application.
Notifications and application status tracking.
Feedback submission.
Guest Interface
A simple layout for exploring scholarships and reading their details.
Getting Started
Prerequisites
Install Java 17 or later.
Install Maven for building the project.
Install Microsoft SQL Server for the database.
Ensure a compatible IDE like IntelliJ IDEA or Eclipse.
Setup Steps
Clone the repository:
git clone https://github.com/your-repo-name/scholarship-management-system.git
Import the project into your IDE as a Maven project.
Configure the DatabaseUtil class with your database credentials.
Run the database scripts provided in schema.sql to set up the database.
Build and run the project using Maven:
mvn clean package
java -jar target/proj.jar
Deployment
GitHub Actions Workflow
The project includes a CI/CD pipeline to automate build and deployment using GitHub Actions.
Artifacts are built with Maven and deployed to a Windows-based server or virtual machine.
Contributing
We welcome contributions! Follow these steps to contribute:

Fork the repository.
Create a feature branch:
git checkout -b feature-branch
Commit your changes:
git commit -m "Add a new feature"
Push to your branch:
git push origin feature-branch
Create a Pull Request.
