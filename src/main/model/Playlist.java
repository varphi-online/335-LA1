package main.model;

import java.util.ArrayList;
import main.view.View;

public class Playlist {
    private final ArrayList<Song> songs;
    private final String name;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    @Override
    public String toString() {
        String out = "";
        out += "═".repeat(57 - name.length() / 2) + "╣ Playlist: " + name + " ╠" + "═".repeat(56 - name.length() / 2)
                + "\n\n";

        out += "│ " + View.format("Song Title", 41) + " │ " + View.format("Artist", 25) + " │ "
                + View.format("Album Title", 30) + " │ "
                + View.format("Genre", 6) + " │ " + View.format("Year", 6) + "│ Rating " + "│ Fav\n" + "├"
                + "─".repeat(42) + "┼" + "─".repeat(26) + "┼" + "─".repeat(31) + "┼" + "─".repeat(7) + "┼"
                + "─".repeat(6) + "┼" + "─".repeat(8) + "┤\n";

        for (Song song : songs) {
            out += song.toString() + "\n";
        }

        out += "└" + "─".repeat(42) + "┴" + "─".repeat(26) + "┴" + "─".repeat(31) + "┴" + "─".repeat(7)
                + "┴" + "─".repeat(6) + "┴" + "─".repeat(8) + "┘";
        return out;
    }

}
