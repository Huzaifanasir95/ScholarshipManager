package application;

public class Notification {
    private String title;     // Title of the notification
    private String message;   // Detailed message
    private String type;      // Type of notification (e.g., "New Scholarship", "Application Response")

    /**
     * Constructor to create a Notification instance.
     *
     * @param title   The title of the notification.
     * @param message The message or details of the notification.
     * @param type    The type of notification (e.g., "New Scholarship", "Application Response").
     */
    public Notification(String title, String message, String type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
