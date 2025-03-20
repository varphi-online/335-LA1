package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

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

    public void addSong(Song song, Boolean addToAlbum) {
        songs.add(song);
        ArrayList<Album> foundAlbums = findAlbumTitle(song.getAlbumTitle(), true);
        if (!foundAlbums.isEmpty()) {
            foundAlbums.get(0).addSong(song.getTitle());
        } else {
            Album album = new Album(song.getAlbumTitle(), song.getArtist(), song.getGenre(), song.getYear());
            album.addSong(song.getTitle());
            addAlbum(album);
        }
    }

    public void addPlaylist(String name) {
        playlists.add(new Playlist(name));
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public void removeSong(String title) {
        ArrayList<Song> found = new ArrayList<>();
        songs.stream().filter(s -> s.getTitle().equals(title)).forEach(s -> found.add(s));
        found.forEach(s -> songs.remove(s));
    }

    public void removeAlbum(String title) {
        ArrayList<Album> found = new ArrayList<>();
        albums.stream().filter(a -> a.getAlbumTitle().equals(title)).forEach(a -> found.add(a));
        found.forEach(a -> {
            ArrayList<Song> foundSongs = new ArrayList<>();
            songs.stream().filter(s -> s.getAlbumTitle().equals(a.getAlbumTitle())).forEach(s -> foundSongs.add(s));
            foundSongs.forEach(s -> songs.remove(s));
            albums.remove(a);
        });
    }

    public Playlist retrieveTopPlayedSongs() {
        ArrayList<Song> found = new ArrayList<>(songs);
        Playlist out = new Playlist("Top Played Songs");
        found = new ArrayList<>(found.stream().filter(s -> s.getPlayCount() > 0).collect(Collectors.toList()));
        found = new ArrayList<>(found.subList(0, Math.min(10, found.size())));
        found.stream().forEach(s -> out.addSong(s));
        return out;
    }

    public Playlist retrieveMostRecentSongs() {
        ArrayList<Song> found = new ArrayList<>(songs);
        Playlist out = new Playlist("Most Recent Songs");
        found.sort((s1, s2) -> s2.getLastPlayed().compareTo(s1.getLastPlayed()));
        found = new ArrayList<>(found.stream().filter(s -> s.getPlayCount() > 0).collect(Collectors.toList()));
        found = new ArrayList<>(found.subList(0, Math.min(10, found.size())));
        found.stream().forEach(s -> out.addSong(s));
        return out;
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

    /*
     * We have to return an array of mutable references because the controller
     * uses this method to add songs, which mutates it.
     */
    public ArrayList<Playlist> findPlaylist(String name) {
        ArrayList<Playlist> found = new ArrayList<>();
        playlists.stream().filter(playlist -> playlist.getName().toLowerCase().contains(name.toLowerCase()))
                .forEach(p -> found.add(p));
        retrieveAutoPlaylists().stream()
                .filter(playlist -> playlist.getName().toLowerCase().contains(name.toLowerCase()))
                .forEach(p -> found.add(p));
        Playlist tops = retrieveTopRatedSongs();
        if ("top rated songs".contains(name.toLowerCase()) && !tops.getSongs().isEmpty())
            found.add(tops);
        Playlist favs = retrieveFavoriteSongsAsPlaylist();
        if ("favorite songs".contains(name.toLowerCase()) && !favs.getSongs().isEmpty())
            found.add(favs);
        return found;
    }

    public ArrayList<Playlist> retrieveAutoPlaylists() {
        ArrayList<Playlist> found = new ArrayList<>();
        HashMap<String, ArrayList<Song>> songsByGenre = new HashMap<>();
        songs.stream().filter(song -> song.getGenre() != null).forEach(song -> {
            if (!songsByGenre.containsKey(song.getGenre())) {
                songsByGenre.put(song.getGenre(), new ArrayList<>(java.util.Collections.singletonList(song)));
            } else {
                songsByGenre.get(song.getGenre()).add(song);
            }
        });
        songsByGenre.entrySet().forEach(entry -> {
            Playlist playlist = new Playlist(entry.getKey() + " Songs");
            entry.getValue().forEach(song -> playlist.addSong(song));
            if (playlist.getSongs().size() >= 10) // if 10+ songs in genre make autoplaylist
                found.add(playlist);
        });
        return found;
    }

    public Playlist retrieveTopRatedSongs() {
        Playlist found = new Playlist("Top Rated Songs");
        songs.stream().filter(song -> song.getRating() >= 4).forEach(s -> found.addSong(s));
        return found;
    }

    public Playlist retrieveFavoriteSongsAsPlaylist() {
        Playlist found = new Playlist("Favorite Songs");
        songs.stream().filter(song -> song.getFavorite()).forEach(s -> found.addSong(s));
        return found;
    }

}
