
import java.util.Optional;

public class Song extends Album {
    private final String title;
    private Optional<Integer> rating = Optional.empty();

    public Song(String t, Album album) {
        super(album.getArtist(), album.getAlbumTitle(), album.getGenre(), album.getYear());
        title = t;
    }

    public Song(Song song) {
        super(song.getArtist(), song.getAlbumTitle(), song.getGenre(), song.getYear());
        title = song.getTitle();
        rating = song.getRating();
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

    public String toStringDebug() {
        return "Song{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", albumTitle='" + albumTitle + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                '}';
    }
}
