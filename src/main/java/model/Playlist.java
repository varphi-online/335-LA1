package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

    /**
     * Pretty print format for View
     */
    @Override
    public String toString() {
        // I couldnt figure out a nice formatting method that would center
        // this in one line, so I just used the .repeat(-center.len/2) bth sides
        String out = "═".repeat(57 - name.length() / 2) + "╣ Playlist: " + name + " ╠" + "═".repeat(56 - name.length() / 2)+ "\n\n";
        String toPrint = songs.stream().map(Object::toString).collect(Collectors.joining("\n"));
        out += """
        │ Song Title                               │ Artist                   │ Album Title                   │ Genre │ Year │ Rating │ Fav
        ├──────────────────────────────────────────┼──────────────────────────┼───────────────────────────────┼───────┼──────┼────────┤
        %s
        └──────────────────────────────────────────┴──────────────────────────┴───────────────────────────────┴───────┴──────┴────────┘
        """
        .formatted(toPrint);
        return out;
    }

}
