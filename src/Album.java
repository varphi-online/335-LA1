public class Album {
    private final String albumTitle;
    private final String artist;
    private final String genre;
    private final int year;

    public Album(String albumTitle, String artist, String genre, int year) {
        this.albumTitle = albumTitle;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }
}
