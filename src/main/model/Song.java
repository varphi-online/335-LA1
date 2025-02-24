package main.model;
import java.util.Optional;
import main.view.View;

public class Song extends Album {
    private final String title;
    private Optional<Integer> rating = Optional.empty();
    private Boolean favorite = false;

    public Song(String t, Album album) {
        super(album.getAlbumTitle(), album.getArtist(), album.getGenre(), album.getYear());
        title = t;
    }

    public Song(Song song) {
        super(song.getAlbumTitle(),song.getArtist(), song.getGenre(), song.getYear());
        title = song.getTitle();
        rating = song.getRating();
    }

    public Optional<Integer> getRating() {
        return rating;
    }

    public void setRating(int newRating) {
        if (newRating < 0 || newRating > 5) {
            rating = Optional.empty();
        } else {
            rating = Optional.of(newRating);
            if (newRating==5) {
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

    public String toString() {
        String out = "";
        //out += "├" + "─".repeat(42) +"┼" + "─".repeat(26) + "┼" + "─".repeat(31) + "┼" + "─".repeat(7) + "┼" + "─".repeat(6) + "┼" + "─".repeat(8) + "┤\n";
        out += "│ " + title +" ".repeat(40-title.length())+ " │ " + View.format(artist, 25) + " │ " + View.format(albumTitle, 30) + " │ "
                + View.format(genre, 6) + " │ " + View.format(String.valueOf(year), 6)+"│ "+ (rating.isEmpty() ? " ":rating.get()) + (favorite ? "      │  *" : "      │");
        return out;
    }
}
