package genius.songs;

import genius.accounts.Artist;
import genius.accounts.User;
import genius.albums.Album;
import genius.comments.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Song {
    private String title;
    private String lyrics;
    private List<Artist> artists;
    private String genre;
    private List<String> tags;
    private int viewsCount;
    private List<Comment> comments;
    private Date releaseDate;
    private Album album;
    private List<User> likes;

    public Song(String title, Artist mainArtist, String lyrics, String genre) {
        this.title = title;
        this.lyrics = lyrics;
        this.artists = new ArrayList<>();
        this.artists.add(mainArtist);
        this.genre = genre;
        this.tags = new ArrayList<>();
        this.viewsCount = 0;
        this.comments = new ArrayList<>();
        this.releaseDate = new Date();
        this.album = null;
        this.likes = new ArrayList<>();
    }

    public void addArtist(Artist artist) {
        if (artist != null && !artists.contains(artist)) {
            artists.add(artist);
        }
    }

    public void addTag(String tag) {
        if (tag != null && !tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void addComment(Comment comment) {
        if (comment != null && !comments.contains(comment)) {
            comments.add(comment);
        }
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public void incrementViews() {
        viewsCount++;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public void likeSong(User user) {
        if (user != null && !likes.contains(user)) {
            likes.add(user);
        }
    }

    public void unlikeSong(User user) {
        likes.remove(user);
    }

    public String getTitle() {
        return title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public List<Artist> getArtists() {
        return new ArrayList<>(artists);
    }

    public String getGenre() {
        return genre;
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    public Date getReleaseDate() {
        return (Date) releaseDate.clone();
    }

    public Album getAlbum() {
        return album;
    }

    public List<User> getLikes() {
        return new ArrayList<>(likes);
    }

    public String getArtistNames() {
        if (artists.isEmpty()) return "Unknown";
        StringBuilder artistNames = new StringBuilder();
        for (Artist artist : artists) {
            artistNames.append(artist.getName()).append(", ");
        }
        if (artistNames.length() > 2) {
            artistNames.setLength(artistNames.length() - 2);
        }
        return artistNames.toString();
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artists=" + getArtistNames() +
                ", genre='" + genre + '\'' +
                ", views=" + viewsCount +
                ", likes=" + likes.size() +
                ", album=" + (album != null ? album.getTitle() : "Single") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return title.equals(song.title) && artists.equals(song.artists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artists);
    }
}