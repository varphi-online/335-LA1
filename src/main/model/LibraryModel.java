package main.model;
import java.util.ArrayList;

public class LibraryModel extends MusicStore {
    private ArrayList<Playlist> playlists;

    public LibraryModel() {
        super();
        this.playlists = new ArrayList<>();
    }

    public void addPlaylist(String name) {
        playlists.add(new Playlist(name));
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }
    
    public ArrayList<Song> getFavorites() {
        ArrayList<Song> found = new ArrayList<>();
        songs.stream().filter(song -> song.getFavorite()).forEach(s -> found.add(s));
        return found;
    }

    public ArrayList<Playlist> findPlaylist(String name) {
        ArrayList<Playlist> found = new ArrayList<>();
        playlists.stream().filter(playlist -> playlist.getName().toLowerCase().contains(name.toLowerCase())).forEach(p -> found.add(p));
        return found;
    }
}
