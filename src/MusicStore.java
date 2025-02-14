import java.util.ArrayList;

public class MusicStore {
    protected ArrayList<Album> albums;
    protected ArrayList<Song> songs;

    

    public MusicStore() {
        this.albums = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    // TODO: We need to implement searching

    public ArrayList<Album> findAlbumTitle(String title) {
        return albums;
    }

    public ArrayList<Album> findAlbumArtist(String name) {
        return albums;
    }

    public ArrayList<Song> findSongTitle(String title) {
        return songs;
    }

    public ArrayList<Song> findSongArtist(String name) {
        return songs;
    }
}
