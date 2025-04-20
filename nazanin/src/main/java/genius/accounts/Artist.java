package genius.accounts;

import java.util.ArrayList;
import java.util.List;

public class Artist extends Account {
    private boolean isApproved;// Whether the artist is approved by an admin
    private List<User> followers;// List of users following this artist

    public Artist(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password, AccountType.ARTIST);
        this.isApproved = false;
        this.followers = new ArrayList<>();
    }
    // Approve the artist
    public void approve() {
        this.isApproved = true;
    }
    // Check if the artist is approved
    public boolean isApproved() {
        return isApproved;
    }
    // Set the approval status of the artist
    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    // Add a follower if not already following
    public void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
        }
    }
    // Remove a follower
    public void removeFollower(User user) {
        followers.remove(user);
    }

    // Return a copy of the followers list
    public List<User> getFollowers() {
        return new ArrayList<>(followers);
    }
}
