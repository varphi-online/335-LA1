public class Song extends Album {
    private final String title;
    private int rating = 0;

    public Song(String t, Album album) {
        super(album.getArtist(), album.getAlbumTitle(), album.getGenre(), album.getYear());
        title = t;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int newRating) {
        rating = newRating;
    }

    public String getTitle() {
        return title;
    }
}
