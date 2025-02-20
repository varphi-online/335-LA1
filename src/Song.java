
import java.util.Objects;
import java.util.Optional;

public class Song extends Album {
    private final String title;
    private Optional<Integer> rating = Optional.empty();
    private final int id;
    private Boolean favorite = false;

    public Song(String t, Album album) {
        super(album.getAlbumTitle(), album.getArtist(), album.getGenre(), album.getYear());
        title = t;
        id = Objects.hash(title, getArtist(), getAlbumTitle(), getGenre(), getYear());

    }

    public Song(Song song) {
        super(song.getArtist(), song.getAlbumTitle(), song.getGenre(), song.getYear());
        title = song.getTitle();
        rating = song.getRating();
        id = song.getId();
    }

    public Optional<Integer> getRating() {
        return rating;
    }

    public void setRating(int newRating) {
        if (newRating < 0 || newRating > 5) {
            rating = Optional.empty();
        } else {
            rating = Optional.of(newRating);
        }
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean value) {
        this.favorite = value;
    }

    public String toString() {
        String out = "+" + "-".repeat(26) + "+" + "-".repeat(13) + "+" + "-".repeat(7) + "+" + "-".repeat(6) + "+\n";
        out += "| " + title +" ".repeat(54-title.length())+ "|\n| " + format(artist, 25) + " | " + format(albumTitle, 12) + " | "
                + format(genre, 6) + " | " + format(String.valueOf(year), 6) + (favorite ? "| ★" : "|");
        return out;
    }

    public String format(String str, int n) {
        String s = str.substring(0, Math.min(n - 1, str.length()));
        int padding = Math.max(0, n - s.length() - 1);
        return s + " ".repeat(padding);
    }
}
