package model;

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

    public ArrayList<Album> findAlbumSong(String title) {
        ArrayList<Album> foundAlbums = new ArrayList<>();
        songs.stream().filter(s -> s.getTitle().contains(title))
                .forEach(s -> foundAlbums.add(new Album((Album)s)));
        return foundAlbums;
    }

    public ArrayList<Song> findSongTitle(String title) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().filter(song -> song.getTitle().toLowerCase().contains(title.toLowerCase()))
                .forEach(song -> foundSongs.add(new Song(song)));
        foundSongs.sort((s1, s2) -> s1.getTitle().compareTo(s2.getTitle()));  // sort ascending by title
        return foundSongs;
    }

    public ArrayList<Song> findSongArtist(String name) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().filter(song -> song.getArtist().toLowerCase().contains(name.toLowerCase()))
                .forEach(song -> foundSongs.add(new Song(song)));
        foundSongs.sort((s1, s2) -> s1.getArtist().compareTo(s2.getArtist()));  // sort ascending by artist
        return foundSongs;
    }

    public ArrayList<Song> findSongRating(Integer rating) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        if (rating != null) {
            // get by rating
            songs.stream().filter(song -> song.getRating().orElse(0).equals(rating))
                    .forEach(song -> foundSongs.add(new Song(song)));
        } else {
            // get all songs, sort by rating descending
            songs.forEach(song -> foundSongs.add(new Song(song)));
            foundSongs.sort((s1, s2) -> s2.getRating().orElse(0).compareTo(s1.getRating().orElse(0)));
        }
        return foundSongs;
    }

    public ArrayList<Song> findSongGenre(String genre) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().filter(song -> song.getGenre().toLowerCase().contains(genre.toLowerCase()))
                .forEach(song -> foundSongs.add(new Song(song)));
        return foundSongs;
    }

    public ArrayList<Song> findSongAlbum(String title) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().filter(song -> song.getAlbumTitle().toLowerCase().contains(title.toLowerCase()))
                .forEach(song -> foundSongs.add(new Song(song)));
        return foundSongs;
    }

    public void shuffleSongs() {
        java.util.Collections.shuffle(songs);
    }

    public ArrayList<Song> getSongs() {
        return new ArrayList<>(songs);
    }
}