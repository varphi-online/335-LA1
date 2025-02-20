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
        albums.stream().filter(album -> album.getAlbumTitle().toLowerCase().contains(title.toLowerCase())).forEach(album -> foundAlbums.add(album));
        return foundAlbums;
    }

    public ArrayList<Album> findAlbumArtist(String name) {
        ArrayList<Album> foundAlbums = new ArrayList<>();
        albums.stream().filter(album -> album.getArtist().toLowerCase().contains(name.toLowerCase())).forEach(album -> foundAlbums.add(album));
        return foundAlbums;
    }

    public ArrayList<Song> findSongTitle(String title) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().filter(song -> song.getTitle().toLowerCase().contains(title.toLowerCase())).forEach(song -> foundSongs.add(song));
        return foundSongs;
    }

    public ArrayList<Song> findSongArtist(String name) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().filter(song -> song.getArtist().toLowerCase().contains(name.toLowerCase())).forEach(song -> System.out.println(song));
        return foundSongs;
    }
    public String toString() {
        return "MusicStore{" +
                "albums=" + albums.toString() +
                ", songs=" + songs.toString() +
                '}';
    }
}
