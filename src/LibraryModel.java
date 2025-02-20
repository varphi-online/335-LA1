import java.util.ArrayList;

public class LibraryModel extends MusicStore {
    private ArrayList<Playlist> playlists;
    private ArrayList<Song> favorites;

    public LibraryModel() {
        super();
        this.playlists = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

    public void addPlaylist(String name) {
        playlists.add(new Playlist(name));
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public ArrayList<Playlist> findPlaylist(String name) {
        ArrayList<Playlist> found = new ArrayList<>();
        playlists.stream().filter(playlist -> playlist.getName().toLowerCase().contains(name)).forEach(found::add);
        return found;
    }
}
