public class Album {
    protected final String albumTitle;
    protected final String artist;
    protected final String genre;
    protected final int year;

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

    public String toString() {
        return "Album{" +
                "albumTitle='" + albumTitle + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                '}';
    }
}
