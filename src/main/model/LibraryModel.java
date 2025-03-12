package main.model;
import java.util.ArrayList;

public class LibraryModel extends MusicStore {
    private final ArrayList<Playlist> playlists;
    private Song nowPlaying;

    public LibraryModel() {
        super();
        this.playlists = new ArrayList<>();
    }

    public boolean playSong(String title) {
        ArrayList<Song> songs = findSongTitle(title);
        if (!songs.isEmpty()) {
            nowPlaying = songs.get(0);
            nowPlaying.play();
            return true;
        }
        return false;
    }

    public Song getNowPlaying() {
        return nowPlaying;
    }

    public void addPlaylist(String name) {
        playlists.add(new Playlist(name));
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public ArrayList<Song> getTopPlayedSongs() {
        ArrayList<Song> found = new ArrayList<>(songs);
        found.sort((s1, s2) -> s2.getPlayCount() - s1.getPlayCount());
        found = new ArrayList<>(found.subList(0, Math.min(10, found.size())));
        return found;
    }

    public ArrayList<Song> getMostRecentSongs() {
        ArrayList<Song> found = new ArrayList<>(songs);
        found.sort((s1, s2) -> s2.getLastPlayed().compareTo(s1.getLastPlayed()));
        found = new ArrayList<>(found.subList(0, Math.min(10, found.size())));
        return found;
    }
    
    public ArrayList<Song> getFavorites() {
        ArrayList<Song> found = new ArrayList<>();
        songs.stream().filter(song -> song.getFavorite()).forEach(s -> found.add(new Song(s)));
        return found;
    }

    // Need to return mutable song to rate and add favorites, as well as 
    // share mutablity between the store itsself and playlists
    @Override
    public ArrayList<Song> findSongTitle(String title) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().filter(song -> song.getTitle().toLowerCase().contains(title.toLowerCase()))
                .forEach(song -> foundSongs.add(song));
        return foundSongs;
    }

    /* We have to return an array of mutable references because the controller 
     * uses this method to add songs, which mutates it. 
     */
    public ArrayList<Playlist> findPlaylist(String name) {
        ArrayList<Playlist> found = new ArrayList<>();
        playlists.stream().filter(playlist -> playlist.getName().toLowerCase().contains(name.toLowerCase())).forEach(p -> found.add(p));
        return found;
    }
}
