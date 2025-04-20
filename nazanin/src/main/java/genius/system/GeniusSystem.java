package genius.system;

import genius.accounts.Account;
import genius.accounts.Artist;
import genius.accounts.User;
import genius.albums.Album;
import genius.songs.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Singleton class representing the core system of Genius
public class GeniusSystem {
    private static GeniusSystem instance = new GeniusSystem(); // Singleton instance

    private List<Account> accounts = new ArrayList<>(); // All registered accounts
    private List<Song> songs = new ArrayList<>();       // All songs in the system
    private List<Album> albums = new ArrayList<>();     // All albums in the system

    // Private constructor to prevent external instantiation
    private GeniusSystem() {
    }

    // Accessor for the singleton instance
    public static GeniusSystem getInstance() {
        return instance;
    }

    // Add an account to the system (no duplicate check)
    public void addAccount(Account account) {
        accounts.add(account);
    }

    // Register a new account (checks for unique username/email)
    public boolean register(Account account) {
        boolean exists = getAllAccounts().stream().anyMatch(a ->
                a.getUsername().equals(account.getUsername()) ||
                        a.getEmail().equals(account.getEmail()));
        if (exists) return false;

        accounts.add(account);
        return true;
    }

    // Attempt to log in using username and password
    public Account login(String username, String password) {
        return accounts.stream()
                .filter(account -> account.getUsername().equals(username) && account.verifyPassword(password))
                .findFirst()
                .orElse(null);
    }

    // Return a copy of all accounts
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }

    // Add a song to the system
    public void addSong(Song song) {
        songs.add(song);
    }

    // Add an album to the system
    public void addAlbum(Album album) {
        albums.add(album);
    }

    // Search for songs by title, artist name, or lyrics
    public List<Object> searchSongs(String query) {
        return songs.stream()
                .filter(song ->
                        song.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                song.getArtists().stream().anyMatch(artist -> artist.getName().toLowerCase().contains(query.toLowerCase())) ||
                                song.getLyrics().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Get all songs by a specific artist
    public List<Song> getSongsByArtist(Artist artist) {
        return songs.stream()
                .filter(song -> song.getArtists().contains(artist))
                .collect(Collectors.toList());
    }

    // Get all albums created by a specific artist
    public List<Album> getAlbumsByArtist(Artist artist) {
        return albums.stream()
                .filter(album -> album.getArtist().equals(artist))
                .collect(Collectors.toList());
    }

    // Find an album by its title (case-insensitive)
    public Album getAlbumByTitle(String title) {
        return albums.stream()
                .filter(album -> album.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    // Get top 10 songs based on view count
    public List<Song> getTopSongs() {
        return songs.stream()
                .sorted(Comparator.comparingInt(Song::getViewsCount).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    // Get list of artists followed by a specific user
    public List<Artist> getFollowedArtists(User user) {
        return user.getFollowingArtists();
    }

    // Get list of users who follow a specific artist
    public List<User> getFollowers(Artist artist) {
        return accounts.stream()
                .filter(account -> account instanceof User)
                .map(account -> (User) account)
                .filter(user -> user.getFollowingArtists().contains(artist))
                .collect(Collectors.toList());
    }
}
