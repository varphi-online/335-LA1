package main.model;

import java.util.ArrayList;

public class MusicStore {
    protected ArrayList<Album> albums;
    protected ArrayList<Song> songs;

    public MusicStore() {
        this.albums = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    /*
     * Using lambda functions, we iterate over the internal database searching
     * case-insensitively for a match with the query parameter, then add a
     * copy-constructed object of the same class to a new ArrayList to
     * preserve proper encapsulation.
     */
    public ArrayList<Album> findAlbumTitle(String title) {
        ArrayList<Album> foundAlbums = new ArrayList<>();
        albums.stream().filter(album -> album.getAlbumTitle().toLowerCase().contains(title.toLowerCase()))
                .forEach(album -> foundAlbums.add(new Album(album)));
        return foundAlbums;
    }

    public ArrayList<Album> findAlbumArtist(String name) {
        ArrayList<Album> foundAlbums = new ArrayList<>();
        albums.stream().filter(album -> album.getArtist().toLowerCase().contains(name.toLowerCase()))
                .forEach(album -> foundAlbums.add(new Album(album)));
        return foundAlbums;
    }

    // Need to return mutable song to rate and add favorites, as well as 
    // share mutablity between the store itsself and playlists
    public ArrayList<Song> findSongTitle(String title) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().filter(song -> song.getTitle().toLowerCase().contains(title.toLowerCase()))
                .forEach(song -> foundSongs.add(song));
        return foundSongs;
    }

    public ArrayList<Song> findSongArtist(String name) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().filter(song -> song.getArtist().toLowerCase().contains(name.toLowerCase()))
                .forEach(song -> foundSongs.add(new Song(song)));
        return foundSongs;
    }

    public ArrayList<Song> findSongAlbum(String title) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().filter(song -> song.getAlbumTitle().toLowerCase().contains(title.toLowerCase()))
                .forEach(song -> foundSongs.add(new Song(song)));
        return foundSongs;
    }
}