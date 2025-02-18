import java.util.ArrayList;

public class MusicStore {
    protected ArrayList<Album> albums;
    protected ArrayList<Song> songs;

    public MusicStore() {
        this.albums = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public ArrayList<Album> findAlbumTitle(String title) {
        ArrayList<Album> foundAlbums = new ArrayList<>();
        for (Album album: albums) {
            if (album.getAlbumTitle().toLowerCase().equals(title)) {
                foundAlbums.add(album);
            }
        }
        return foundAlbums;
    }

    public ArrayList<Album> findAlbumArtist(String name) {
        ArrayList<Album> foundAlbums = new ArrayList<>();
        for (Album album: albums) {
            if (album.getArtist().toLowerCase().equals(name)) {
                foundAlbums.add(album);
            }
        }
        return foundAlbums;
    }

    public ArrayList<Song> findSongTitle(String title) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        for (Song song: songs) {
            if (song.getTitle().toLowerCase().equals(title)) {
                foundSongs.add(new Song(song));
            }
        }
        return foundSongs;
    }

    public ArrayList<Song> findSongArtist(String name) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        for (Song song: songs) {
            if (song.getArtist().toLowerCase().equals(name)) {
                foundSongs.add(new Song(song));
            }
        }
        return foundSongs;
    }
    public String toString() {
        return "MusicStore{" +
                "albums=" + albums.toString() +
                ", songs=" + songs.toString() +
                '}';
    }
}
