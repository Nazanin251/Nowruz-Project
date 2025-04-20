package genius.comments;

import genius.accounts.User;
import java.util.Date;

// Comment class represents a comment made by a user
public class Comment {
    private User user;      // The user who made the comment
    private String content; // The content of the comment
    private Date date;      // The date and time when the comment was made

    // Constructor initializes the comment with current date
    public Comment(User user, String content) {
        this.user = user;
        this.content = content;
        this.date = new Date(); // Set timestamp to current date/time
    }

    // Get the user who wrote the comment
    public User getUser() {
        return user;
    }

    // Get the text content of the comment
    public String getContent() {
        return content;
    }

    // Return a clone of the comment's date to prevent external modification
    public Date getDate() {
        return (Date) date.clone();
    }

    // String representation of the comment
    @Override
    public String toString() {
        return "Comment by " + user.getName() + " on " + date + ": " + content;
    }
}
