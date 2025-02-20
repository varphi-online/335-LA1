
import java.util.Objects;
import java.util.Optional;


public class Song extends Album {
    private final String title;
    private Optional<Integer> rating = Optional.empty();
    private final int id;
    private Boolean favorite = false;

    public Song(String t, Album album) {
        super(album.getAlbumTitle() ,album.getArtist(), album.getGenre(), album.getYear());
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
        rating = Optional.of(newRating);
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
        return title+" | "+artist+" | "+id+" | "+albumTitle+" | "+genre+" | "+year+" | "+(rating.isPresent() ? rating.get() : "No rating");
    }
}
