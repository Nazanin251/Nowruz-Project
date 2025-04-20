package genius.albums;

import genius.accounts.Artist;
import genius.songs.Song;

import java.util.ArrayList;
import java.util.List;

// Album class representing a music album with a list of songs
public class Album {
    private String title;
    private Artist artist;
    private String releaseDate;
    private List<Song> tracklist; // List of songs in the album

    // Constructor to initialize album details
    public Album(String title, Artist artist, String releaseDate) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.tracklist = new ArrayList<>();
    }

    // Add a song to the album if not already in the tracklist
    public void addSong(Song song) {
        if (song != null && !tracklist.contains(song)) {
            tracklist.add(song);
            song.setAlbum(this); // Set back-reference from song to this album
        }
    }

    // Return a copy of the tracklist
    public List<Song> getTracklist() {
        return new ArrayList<>(tracklist);
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    // Get the total number of tracks in the album
    public int getTrackCount() {
        return tracklist.size();
    }

    // String representation of the album
    @Override
    public String toString() {
        return "Album{" +
                "title='" + title + '\'' +
                ", artist='" + artist.getName() + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", trackCount=" + tracklist.size() +
                '}';
    }

    // Albums are equal if they have the same title and artist
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return title.equals(album.title) && artist.equals(album.artist);
    }

    // Generate hash code based on title and artist
    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + artist.hashCode();
        return result;
    }
}
