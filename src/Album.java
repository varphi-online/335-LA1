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
        return "+"+"-".repeat(70)+"\n"+"| " + albumTitle + " | " +format(artist, 25) + " | " + format(genre, 15) + " | " + year+"\n"+"+"+"-".repeat(70);
    }

    public String format(String str, int n) {
        String s = str.substring(0, Math.min(n - 1, str.length()));
        int padding = Math.max(0, n - s.length() - 1);
        return s + " ".repeat(padding);
    }
}
