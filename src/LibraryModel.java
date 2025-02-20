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

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public ArrayList<Playlist> findPlaylist(String name) {
        ArrayList<Playlist> found = new ArrayList<>();
        playlists.stream().filter(playlist -> playlist.getName().toLowerCase().contains(name)).forEach(p -> found.add(p));
        return found;
    }
}
