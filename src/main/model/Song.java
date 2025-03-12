package main.model;

import java.sql.Timestamp;
import java.util.Optional;

public class Song extends Album {
    private final String title;
    private Optional<Integer> rating = Optional.empty();
    private Boolean favorite = false;
    private int playCount = 0;
    private Timestamp lastPlayed = new Timestamp(System.currentTimeMillis());

    public Song(String t, Album album) {
        super(album.getAlbumTitle(), album.getArtist(), album.getGenre(), album.getYear());
        title = t;
    }

    public Song(Song song) {
        super(song.getAlbumTitle(), song.getArtist(), song.getGenre(), song.getYear());
        title = song.getTitle();
        rating = song.getRating();
        favorite = song.getFavorite();
        playCount = song.getPlayCount();
        lastPlayed = song.getLastPlayed();
    }

    public void play() {
        playCount++;
        lastPlayed = new Timestamp(System.currentTimeMillis());
    }

    public int getPlayCount() {
        return playCount;
    }

    public Timestamp getLastPlayed() {
        return lastPlayed;
    }

    public Optional<Integer> getRating() {
        return rating;
    }
    
    public void setRating(int newRating) {
        // Any invalid ratings remove the rating
        if (newRating < 0 || newRating > 5) {
            rating = Optional.empty();
        } else {
            rating = Optional.of(newRating);
            if (newRating == 5) {
                setFavorite(true);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean value) {
        this.favorite = value;
    }

    /**
     * Pretty print format for View
     */
    public String toString() {
        return String.format(
                "│ %-40s │ %-24s │ %-29s │ %-5.5s │ %-4d │   %-4s │ %s",
                title, artist, albumTitle, genre, year, (rating.isEmpty() ? " " : "" + rating.get()),
                (favorite ? " *" : ""));
    }
}
