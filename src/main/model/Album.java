package main.model;
import main.view.View;

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
        String out = "";
        //out += "├" + "─".repeat(31) +"┼" + "─".repeat(21) + "┼" + "─".repeat(26) + "┼" + "─".repeat(6) + "┤\n";
        out += "│ " + View.format(albumTitle, 30) + " │ " + View.format(artist, 20) + " │ " + 
               View.format(genre, 25) + " │ " + year + " │";
        return out;
    }
}
