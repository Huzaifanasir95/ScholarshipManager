package application;

import java.util.Objects;

public class Bookmark {
    private int bookmarkId;         // Unique ID for the bookmark
    private int userId;             // ID of the user who created the bookmark
    private int scholarshipId;      // ID of the scholarship bookmarked
    private String scholarshipName; // Name of the bookmarked scholarship
    private String createdAt;       // Timestamp when the bookmark was created

    // Constructor
    public Bookmark(int bookmarkId, int userId, int scholarshipId, String scholarshipName, String createdAt) {
        this.bookmarkId = bookmarkId;
        this.userId = userId;
        this.scholarshipId = scholarshipId;
        this.scholarshipName = scholarshipName;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScholarshipId() {
        return scholarshipId;
    }

    public void setScholarshipId(int scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public String getScholarshipName() {
        return scholarshipName;
    }

    public void setScholarshipName(String scholarshipName) {
        this.scholarshipName = scholarshipName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Equals and hashCode (useful for collections)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return bookmarkId == bookmark.bookmarkId &&
               userId == bookmark.userId &&
               scholarshipId == bookmark.scholarshipId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookmarkId, userId, scholarshipId);
    }

    // ToString method
    @Override
    public String toString() {
        return "Bookmark{" +
               "bookmarkId=" + bookmarkId +
               ", userId=" + userId +
               ", scholarshipId=" + scholarshipId +
               ", scholarshipName='" + scholarshipName + '\'' +
               ", createdAt='" + createdAt + '\'' +
               '}';
    }
}
