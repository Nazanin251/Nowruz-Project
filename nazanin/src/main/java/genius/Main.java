package genius;

import genius.accounts.Account;
import genius.accounts.Admin;
import genius.accounts.Artist;
import genius.accounts.User;
import genius.albums.Album;
import genius.comments.Comment;
import genius.songs.Song;
import genius.system.GeniusSystem;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final GeniusSystem system = GeniusSystem.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeSampleData();
        showMainMenu();
    }

    private static void initializeSampleData() {
        Admin admin = new Admin("System Admin", 35, "admin@genius.com", "admin", "Admin@1234");
        system.addAccount(admin);

        Artist artist1 = new Artist("Saeed Asayesh", 30, "asayesh@example.com", "asayesh", "asayesh@123");
        Artist artist2 = new Artist("Mohsen Chavoshi", 45, "chavoshi@example.com", "chavoshi", "chavoshi@123");


        admin.verifyArtist(artist1);
        admin.verifyArtist(artist2);

        User user1 = new User("Sample User", 25, "user1@example.com", "user1", "User@1234");
        User user2 = new User("Second User", 28, "user2@example.com", "user2", "User@5678");

        system.addAccount(artist1);
        system.addAccount(artist2);
        system.addAccount(user1);
        system.addAccount(user2);

        Song song1 = new Song("Ghorboonet Beram", artist1, "ghorboonet beram dele man khoone ...", "Pop");
        Song song2 = new Song("Mohreye Mar", artist1, "Eshgh to doorough bood dige ....", "Pop");
        system.addSong(song1);
        system.addSong(song2);

        Album album1 = new Album("Tabe Eshgh", artist1, "2025-01-01");
        system.addAlbum(album1);
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n===== Genius Music Platform =====");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Thank you for using. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Account account = system.login(username, password);

        if (account == null) {
            System.out.println("Incorrect username or password.");
            return;
        }

        if (account instanceof Artist && !((Artist) account).isApproved()) {
            System.out.println("Your artist account is not yet approved by an admin. Please wait for approval.");
            return;
        }

        System.out.println("Login successful. Welcome " + account.getName() + "!");
        showUserMenu(account);
    }

    private static void register() {
        System.out.println("\n=== Register ===");
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.println("Account Type:");
        System.out.println("1. Regular User");
        System.out.println("2. Artist");
        System.out.println("3. Admin");
        System.out.print("Please choose: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        Account account;
        if (type == 1) {
            account = new User(name, age, email, username, password);
        } else if (type == 2) {
            account = new Artist(name, age, email, username, password);
            System.out.println("Artist account needs to be approved by an admin.");
        } else if (type == 3) {
            account = new Admin(name, age, email, username, password);
            System.out.println("Admin account created successfully.");
        } else {
            System.out.println("Invalid option.");
            return;
        }

        if (system.register(account)) {
            if (account instanceof Artist) {
                System.out.println("Registration successful. Please wait for admin approval before logging in.");
            } else {
                System.out.println("Registration successful. Please log in.");
            }
        } else {
            System.out.println("Username or email already in use.");
        }
    }

    private static void showUserMenu(Account account) {
        if (account instanceof User) {
            showRegularUserMenu((User) account);
        } else if (account instanceof Artist) {
            showArtistMenu((Artist) account);
        } else if (account instanceof Admin) {
            showAdminMenu((Admin) account);
        }
    }

    private static void showRegularUserMenu(User user) {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Search Songs and View Lyrics");
            System.out.println("2. View Artists");
            System.out.println("3. Follow an Artist");
            System.out.println("4. View Followed Artists");
            System.out.println("5. View Popular Songs");
            System.out.println("6. Add Comment to Song");
            System.out.println("7. Remove Comment from Song");
            System.out.println("8. Like/Unlike Song");
            System.out.println("9. Log Out");
            System.out.print("Please choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchSongsAndViewLyrics(user);
                    break;
                case 2:
                    viewArtists();
                    break;
                case 3:
                    followArtist(user);
                    break;
                case 4:
                    viewFollowedArtists(user);
                    break;
                case 5:
                    viewPopularSongs();
                    break;
                case 6:
                    addCommentToSong(user);
                    break;
                case 7:
                    removeCommentFromSong(user);
                    break;
                case 8:
                    likeUnlikeSong(user);
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void searchSongsAndViewLyrics(User user) {
        System.out.print("Search term (song title, artist, or lyrics): ");
        String query = scanner.nextLine();

        List<Object> results = system.searchSongs(query);
        if (results.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }

        System.out.println("\n=== Search Results ===");
        for (int i = 0; i < results.size(); i++) {
            Song song = (Song) results.get(i);
            System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtistNames() + " (Likes: " + song.getLikes().size() + ")");
            String lyricsPreview = song.getLyrics().length() > 30 ? song.getLyrics().substring(0, 30) + "..." : song.getLyrics();
            System.out.println("   Lyrics preview: " + lyricsPreview);
        }

        System.out.print("Enter the number of the song to view full lyrics (or 0 to cancel): ");
        int songChoice = scanner.nextInt();
        scanner.nextLine();

        if (songChoice == 0 || songChoice > results.size()) {
            System.out.println("Returning to menu...");
            return;
        }

        Song selectedSong = (Song) results.get(songChoice - 1);
        System.out.println("\n=== Lyrics for " + selectedSong.getTitle() + " ===");
        System.out.println(selectedSong.getLyrics());
        selectedSong.incrementViews();
        System.out.println("Views: " + selectedSong.getViewsCount());
        System.out.println("Likes: " + selectedSong.getLikes().size());
        System.out.println("Comments:");
        selectedSong.getComments().forEach(c -> System.out.println(c.getUser().getName() + ": " + c.getContent()));
    }

    private static void addCommentToSong(User user) {
        System.out.print("Enter song title to comment on: ");
        String songTitle = scanner.nextLine();
        Song song = system.searchSongs(songTitle).stream()
                .map(s -> (Song) s)
                .findFirst()
                .orElse(null);

        if (song == null) {
            System.out.println("Song not found.");
            return;
        }

        System.out.print("Enter your comment: ");
        String content = scanner.nextLine();
        Comment comment = new Comment(user, content);
        song.addComment(comment);
        System.out.println("Comment added successfully!");
    }

    private static void removeCommentFromSong(User user) {
        System.out.print("Enter song title to remove comment from: ");
        String songTitle = scanner.nextLine();
        Song song = system.searchSongs(songTitle).stream()
                .map(s -> (Song) s)
                .findFirst()
                .orElse(null);

        if (song == null) {
            System.out.println("Song not found.");
            return;
        }

        List<Comment> userComments = song.getComments().stream()
                .filter(c -> c.getUser().equals(user))
                .toList();

        if (userComments.isEmpty()) {
            System.out.println("You have no comments on this song.");
            return;
        }

        System.out.println("Your comments:");
        for (int i = 0; i < userComments.size(); i++) {
            System.out.println((i + 1) + ". " + userComments.get(i).getContent());
        }

        System.out.print("Enter the number of the comment to remove (or 0 to cancel): ");
        int commentChoice = scanner.nextInt();
        scanner.nextLine();

        if (commentChoice == 0 || commentChoice > userComments.size()) {
            System.out.println("Returning to menu...");
            return;
        }

        Comment commentToRemove = userComments.get(commentChoice - 1);
        song.removeComment(commentToRemove);
        System.out.println("Comment removed successfully!");
    }

    private static void likeUnlikeSong(User user) {
        System.out.print("Enter song title to like/unlike: ");
        String songTitle = scanner.nextLine();
        Song song = system.searchSongs(songTitle).stream()
                .map(s -> (Song) s)
                .findFirst()
                .orElse(null);

        if (song == null) {
            System.out.println("Song not found.");
            return;
        }

        if (song.getLikes().contains(user)) {
            song.unlikeSong(user);
            System.out.println("You unliked '" + song.getTitle() + "'. Likes: " + song.getLikes().size());
        } else {
            song.likeSong(user);
            System.out.println("You liked '" + song.getTitle() + "'. Likes: " + song.getLikes().size());
        }
    }

    private static void followArtist(User user) {
        System.out.print("Enter artist name to follow: ");
        String artistName = scanner.nextLine();

        Artist artistToFollow = system.getAllAccounts().stream()
                .filter(account -> account instanceof Artist && account.getName().equalsIgnoreCase(artistName))
                .map(account -> (Artist) account)
                .findFirst()
                .orElse(null);

        if (artistToFollow == null) {
            System.out.println("Artist not found.");
            return;
        }

        if (user.getFollowingArtists().contains(artistToFollow)) {
            System.out.println("You are already following " + artistToFollow.getName() + ".");
        } else {
            user.followArtist(artistToFollow);
            System.out.println("You are now following " + artistToFollow.getName() + "!");
        }
    }

    private static void viewPopularSongs() {
        System.out.println("\n=== Top Songs by Views ===");
        system.getTopSongs().forEach(song -> {
            System.out.println("Title: " + song.getTitle());
            System.out.println("Artist: " + (song.getArtists().isEmpty() ? "Unknown" : song.getArtists().get(0).getName()));
            System.out.println("Views: " + song.getViewsCount());
            System.out.println("Likes: " + song.getLikes().size());
            System.out.println("--------------------");
        });
    }

    private static void showArtistMenu(Artist artist) {
        while (true) {
            System.out.println("\n=== Artist Menu ===");
            System.out.println("1. Manage Songs");
            System.out.println("2. Manage Albums");
            System.out.println("3. View Followers");
            System.out.println("4. Log Out");
            System.out.print("Please choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageSongs(artist);
                    break;
                case 2:
                    manageAlbums(artist);
                    break;
                case 3:
                    viewFollowers(artist);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void viewFollowers(Artist artist) {
        System.out.println("\n=== Followers of " + artist.getName() + " ===");
        if (artist.getFollowers().isEmpty()) {
            System.out.println("No followers yet.");
        } else {
            artist.getFollowers().forEach(u -> {
                System.out.println("Username: " + u.getUsername());
                System.out.println("Name: " + u.getName());
                System.out.println("--------------------");
            });
        }
    }

    private static void manageAlbums(Artist artist) {
        while (true) {
            System.out.println("\n=== Manage Albums ===");
            System.out.println("1. Create New Album");
            System.out.println("2. Add Song to Album");
            System.out.println("3. View My Albums");
            System.out.println("4. Back to Artist Menu");
            System.out.print("Please choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Album Title: ");
                    String albumTitle = scanner.nextLine();
                    System.out.print("Release Date (e.g., 2025-04-04): ");
                    String releaseDate = scanner.nextLine();
                    Album newAlbum = new Album(albumTitle, artist, releaseDate);
                    system.addAlbum(newAlbum);
                    System.out.println("Album created successfully!");
                    break;
                case 2:
                    System.out.print("Album Title: ");
                    String targetAlbumTitle = scanner.nextLine();
                    Album album = system.getAlbumByTitle(targetAlbumTitle);
                    if (album == null || !album.getArtist().equals(artist)) {
                        System.out.println("Album not found or you don’t have permission.");
                        break;
                    }
                    System.out.println("Your songs:");
                    List<Song> artistSongs = system.getSongsByArtist(artist);
                    if (artistSongs.isEmpty()) {
                        System.out.println("You have no songs to add.");
                        break;
                    }
                    artistSongs.forEach(song ->
                            System.out.println("- " + song.getTitle() + " (In album: " + (song.getAlbum() != null ? song.getAlbum().getTitle() : "None") + ")"));

                    System.out.print("Song Title to Add: ");
                    String songTitleToAdd = scanner.nextLine();
                    Song song = system.getSongsByArtist(artist).stream()
                            .filter(s -> s.getTitle().equalsIgnoreCase(songTitleToAdd))
                            .findFirst()
                            .orElse(null);
                    if (song == null) {
                        System.out.println("Song not found in your songs.");
                    } else if (song.getAlbum() != null && song.getAlbum() != album) {
                        System.out.println("Song is already in another album: " + song.getAlbum().getTitle());
                    } else {
                        album.addSong(song);
                        System.out.println("Song '" + song.getTitle() + "' added to album '" + album.getTitle() + "'!");
                    }
                    break;
                case 3:
                    System.out.println("\n=== My Albums ===");
                    List<Album> artistAlbums = system.getAlbumsByArtist(artist);
                    if (artistAlbums.isEmpty()) {
                        System.out.println("You have no albums yet.");
                    } else {
                        artistAlbums.forEach(album1 -> {
                            System.out.println("Title: " + album1.getTitle());
                            System.out.println("Release Date: " + album1.getReleaseDate());
                            System.out.println("Songs: " + album1.getTracklist().size());
                            System.out.println("Tracklist: ");
                            List<Song> tracks = album1.getTracklist();
                            if (tracks.isEmpty()) {
                                System.out.println("  (No songs in this album)");
                            } else {
                                tracks.forEach(s ->
                                        System.out.println("  - " + s.getTitle() + " by " + s.getArtistNames()));
                            }
                            System.out.println("--------------------");
                        });
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void manageSongs(Artist artist) {
        while (true) {
            System.out.println("\n=== Manage Songs ===");
            System.out.println("1. Add New Song");
            System.out.println("2. Edit Song Lyrics");
            System.out.println("3. View My Songs");
            System.out.println("4. Back to Artist Menu");
            System.out.print("Please choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Song Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Lyrics: ");
                    String lyrics = scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();
                    Song newSong = new Song(title, artist, lyrics, genre);
                    system.addSong(newSong);
                    System.out.println("Song added successfully!");
                    break;
                case 2:
                    System.out.print("Enter Song Title to Edit: ");
                    String songTitle = scanner.nextLine();
                    Song songToEdit = system.searchSongs(songTitle).stream()
                            .filter(s -> s instanceof Song && ((Song) s).getArtists().contains(artist))
                            .map(s -> (Song) s)
                            .findFirst()
                            .orElse(null);
                    if (songToEdit != null) {
                        System.out.print("New Lyrics: ");
                        String newLyrics = scanner.nextLine();
                        songToEdit.setLyrics(newLyrics);
                        System.out.println("Lyrics updated successfully!");
                    } else {
                        System.out.println("Song not found or you don’t have permission to edit it.");
                    }
                    break;
                case 3:
                    System.out.println("\n=== My Songs ===");
                    system.getSongsByArtist(artist).forEach(song -> {
                        System.out.println("Title: " + song.getTitle());
                        System.out.println("Views: " + song.getViewsCount());
                        System.out.println("Likes: " + song.getLikes().size());
                        System.out.println("--------------------");
                    });
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void showAdminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Approve Artists");
            System.out.println("2. View All Users");
            System.out.println("3. Log Out");
            System.out.print("Please choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    approveArtists(admin);
                    break;
                case 2:
                    admin.viewAllUsers();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void approveArtists(Admin admin) {
        System.out.println("List of unapproved artists:");
        admin.getUnapprovedArtists().forEach(artist -> {
            System.out.println("Name: " + artist.getName());
            System.out.println("Email: " + artist.getEmail());
            System.out.print("Do you want to approve this artist? (yes/no): ");
            String approval = scanner.nextLine();
            if (approval.equalsIgnoreCase("yes")) {
                admin.approveArtist(artist);
                System.out.println("Artist " + artist.getName() + " approved.");
            } else {
                System.out.println("Artist not approved.");
            }
        });
    }

    private static void viewArtists() {
        System.out.println("\n=== Artists ===");
        system.getAllAccounts().stream().filter(account -> account instanceof Artist).forEach(account -> {
            Artist artist = (Artist) account;
            System.out.println("Name: " + artist.getName());
            System.out.println("=== Songs ===");
            system.getSongsByArtist(artist).forEach(song -> {
                System.out.println("Title: " + song.getTitle());
                System.out.println("Release Date: " + song.getReleaseDate());
                System.out.println("Views: " + song.getViewsCount());
                System.out.println("Likes: " + song.getLikes().size());
                System.out.println("--- Comments ---");
                song.getComments().forEach(comment -> {
                    System.out.println("Writer: " + comment.getUser().getName());
                    System.out.println("Content: " + comment.getContent());
                    System.out.println("--------------------");
                });
            });
            System.out.println("=== Albums ===");
            system.getAlbumsByArtist(artist).forEach(album -> {
                System.out.println("Title: " + album.getTitle());
                System.out.println("Release Date: " + album.getReleaseDate());
                System.out.println("Track Count: " + album.getTracklist().size());
                System.out.println("--------------------");
            });
            System.out.println("Followers count: " + system.getFollowers(artist).size());
            System.out.println("--------------------");
        });
    }

    private static void viewFollowedArtists(User user) {
        System.out.println("\n=== Followed Artists ===");
        List<Artist> followedArtists = user.getFollowingArtists();
        if (followedArtists.isEmpty()) {
            System.out.println("You are not following any artists yet.");
        } else {
            followedArtists.forEach(artist -> {
                System.out.println("Name: " + artist.getName());
                System.out.println("Followers count: " + system.getFollowers(artist).size());
                System.out.println("Songs: " + system.getSongsByArtist(artist).size());
                System.out.println("--------------------");
            });
        }
    }
}