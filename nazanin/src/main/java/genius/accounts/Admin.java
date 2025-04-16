package genius.accounts;

import genius.system.GeniusSystem;
import java.util.List;
import java.util.stream.Collectors;

public class Admin extends Account {
    // Constructor for Admin account
    public Admin(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password, AccountType.ADMIN);
    }
    // Approve an artist account
    public void verifyArtist(Artist artist) {
        artist.setApproved(true);
        System.out.println("Artist " + artist.getName() + " has been approved.");
    }
    // Display all registered users in the system
    public void viewAllUsers() {
        System.out.println("\n=== View All Users ===");
        GeniusSystem.getInstance().getAllAccounts().forEach(account -> System.out.println(account.getName() + " (" + account.getAccountType() + ")"));
    }
    // Retrieve a list of all artists who haven't been approved yet
    public List<Artist> getUnapprovedArtists() {
        return GeniusSystem.getInstance().getAllAccounts().stream()
                .filter(account -> account instanceof Artist && !((Artist) account).isApproved())
                .map(account -> (Artist) account)
                .collect(Collectors.toList());
    }
    // Approve an artist account (duplicate of verifyArtist)
    public void approveArtist(Artist artist) {
        artist.setApproved(true);
        System.out.println("Artist " + artist.getName() + " has been approved.");
    }
}
