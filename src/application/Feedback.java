package application;

import java.time.LocalDateTime;

public class Feedback {

    private int feedbackId; // Unique ID for feedback (if needed)
    private int userId; // ID of the user providing the feedback
    private String scholarshipName; // Name of the scholarship the feedback is about
    private double rating; // Rating out of 5
    private String review; // Optional review text
    private LocalDateTime submittedAt; // Timestamp of when the feedback was submitted

    // Constructor for creating feedback objects
    public Feedback(int userId, String scholarshipName, double rating, String review, LocalDateTime submittedAt) {
        this.userId = userId;
        this.scholarshipName = scholarshipName;
        this.rating = rating;
        this.review = review;
        this.submittedAt = submittedAt;
    }

    // Getter and Setter for feedbackId
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    // Getter and Setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter and Setter for scholarshipName
    public String getScholarshipName() {
        return scholarshipName;
    }

    public void setScholarshipName(String scholarshipName) {
        this.scholarshipName = scholarshipName;
    }

    // Getter and Setter for rating
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    // Getter and Setter for review
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    // Getter and Setter for submittedAt
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", userId=" + userId +
                ", scholarshipName='" + scholarshipName + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
