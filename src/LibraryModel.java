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

    public Playlist findPlaylist(String name) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equalsIgnoreCase(name)) {
                return playlist;
            }
        }
        return null;
    }
}
