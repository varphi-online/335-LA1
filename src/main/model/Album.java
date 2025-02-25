package main.model;

import java.util.ArrayList;

public class Album {
    protected final String albumTitle;
    protected final String artist;
    protected final String genre;
    protected final int year;
    private ArrayList<String> songs = new ArrayList<>();

    public Album(String albumTitle, String artist, String genre, int year) {
        this.albumTitle = albumTitle;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
    }

    public Album(Album album){
        this.albumTitle = album.albumTitle;
        this.artist = album.artist;
        this.genre = album.genre;
        this.year = album.year;
        this.songs = album.getSongList();
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public ArrayList<String> getSongList(){
        ArrayList<String> out = new ArrayList<>();
        for (String song : songs){
            out.add(song);
        }
        return out;
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

    public void addSong(String song) {
        songs.add(song);
    }

    /**
     * Pretty print format for View
     */
    @Override
    public String toString() {
        String out = """
                ├───────────────────────────────┬─────────────────────┬──────────────────────────┬──────┤
                │ %-29s │ %-19s │ %-24s │ %-4d │
                ├───────────────────────────────┴─────────────────────┴──────────────────────────┴──────┤"""
                .formatted(albumTitle, artist, genre, year);
        // List songs in the order they were added to the arraylist with its index (1-indexed)
        for (int i = 0; i < songs.size(); i++) {
            out+= String.format("\n│     %-3s %-77s │", (i + 1) + ".", songs.get(i)); 
        }
        return out;
    }

}
