package genius.accounts;

import java.util.ArrayList;
import java.util.List;
// User class representing regular user accounts

public class User extends Account {
    private List<Artist> followingArtists;

    public User(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password, AccountType.USER);
        this.followingArtists = new ArrayList<>();
    }

    // Follow an artist if not already followed
    public void followArtist(Artist artist) {
        if (artist != null && !followingArtists.contains(artist)) {
            followingArtists.add(artist);
        }
    }

    // Get a copy of the list of followed artists
    public List<Artist> getFollowingArtists() {
        return new ArrayList<>(followingArtists);
    }
}