package controller;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import model.Album;
import model.LibraryModel;
import model.MusicStore;
import model.Playlist;
import model.Song;
import model.User;
import view.View;

/**
 * Controller class for the Music Store application
 * Creates View and Models, linking View inputs back
 * to itself for processing of Model(s)
 */
public class Controller {
    private static MusicStore musicStore;
    private static View view;
    private static HashMap<String, User> userDB;
    private final static ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module())
            .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    private User currentUser;

    public static void main(String[] args) throws Exception {
        Controller controller = new Controller();
        view = new View();
        view.UI(controller);
    }

    /**
     * Constructor for Controller
     * Initializes the MusicStore and LibraryModel from the albums.txt file
     * 
     * @throws IOException
     */
    public Controller() throws IOException {
        musicStore = new MusicStore();
        File file = new File("./albums/albums.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] albumInfo = line.split(",");
            File albumFile = new File("./albums/" + albumInfo[0] + "_" + albumInfo[1] + ".txt");
            parseAlbum(albumFile);
        }
        br.close();
        this.userDB = objectMapper.readValue(new File("userData.json"), new TypeReference<HashMap<String, User>>() {
        });
        updateDB();
    }

    /**
     * Parses provided album file and adds it to the MusicStore
     * 
     * @param file
     * @throws IOException
     */
    private void parseAlbum(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String[] albumInfo = line.split(",");
        String albumTitle = albumInfo[0];
        String artist = albumInfo[1];
        String genre = albumInfo[2];
        int year = Integer.parseInt(albumInfo[3]);
        Album album = new Album(albumTitle, artist, genre, year);
        while ((line = br.readLine()) != null) {
            String title = line;
            Song song = new Song(title, album);
            album.addSong(title);
            musicStore.addSong(song);
        }
        musicStore.addAlbum(album);
        br.close();
    }

    public MusicStore getMusicStore() {
        return musicStore;
    }

    public void logout() {
        currentUser = null;
    }

    public Boolean login(String username, String password) {
        try {
            if (User.login("users.csv", username, password)) {
                currentUser = userDB.get(username);
                return true;
            }
            view.alert("User not found");
            return false;
        } catch (Exception e) {
            view.error(e);
        }
        return false;
    }

    public final void updateDB(){
        try (FileWriter writer = new FileWriter("userData.json")) {
            writer.write(objectMapper.writeValueAsString(userDB));
        } catch (IOException e) {
            view.error(e);
        }
    }

    public Boolean createUser(String username, String password) {
        try {
            if (User.login("users.csv", username, password)){
                view.error(new Exception("User already exists"));
                return false;
            }
            currentUser = new User("users.csv", username, password);
            userDB.put(username, currentUser);
            updateDB();
            return true;
        } catch (Exception e) {
            view.error(e);
        }
        return false;
    }

    /*
     * Handles input from the View, dispatching
     * operation to the selected store
     * 
     * @param input
     * 
     * @param mode
     */
    public void handleInput(String input, Boolean mode) {
        if (mode) {
            dispatchCommand(input, musicStore);
        } else {
            dispatchCommand(input, currentUser.getLibrary());
        }
    }

    /**
     * Dispatches the command to the appropriate
     * method, operating on the correct store T
     * 
     * @param <T>
     * @param input
     * @param store
     */
    @SuppressWarnings("UseSpecificCatch")
    public <T extends MusicStore> void dispatchCommand(String input, T store) {
        if (input.length() >= 2) {
            String[] inputArray = input.split(" ", 2);
            String command = inputArray[0];
            String query = inputArray.length > 1 ? inputArray[1] : "";
            switch (command.charAt(0)) {
                case '?' -> search(command, query, store);
                case '+' -> add(command, query, store);
                case '-' -> remove(command, query, store);
                case '*' -> rate(command, query, store);
                case '#' -> favorite(command, query, store);
                case 'p' -> play(command, query, store);
                case '&' -> shuffle(command, query, store);
                default -> view.invalid();
            }
        } else {
            if (input.equals("#")) {
                favorite("#", "", store);
            } else {
                view.invalid();
            }
        }
        try {
            updateDB();
        } catch (Exception e) {
            view.error(e);
        }
    }

    /**
     * Process user command as a search command
     * 
     * @param <T>
     * @param command
     * @param query
     * @param store
     */
    private <T extends MusicStore> void search(String command, String query, T store) {
        switch (command.charAt(1)) {
            case 'a' -> view.printResults(store.findAlbumTitle(query));
            case 's' -> view.printResults(store.findSongTitle(query));
            case 'A' -> view.printResults(store.findAlbumArtist(query));
            case 'S' -> view.printResults(store.findSongArtist(query));
            case 'p' -> {
                if (store instanceof LibraryModel) {
                    view.printResults(currentUser.getLibrary().findPlaylist(query));
                } else {
                    view.invalid();
                }
            }
            case 'n' -> {
                if (store instanceof LibraryModel) {
                    view.printResults(currentUser.getLibrary().retrieveMostRecentSongs());
                } else {
                    view.invalid();
                }
            }
            case 'N' -> {
                if (store instanceof LibraryModel) {
                    view.printResults(currentUser.getLibrary().retrieveTopPlayedSongs());
                } else {
                    view.invalid();
                }
            }
            case 'g' -> view.printResults(store.findSongGenre(query));
            case 'f' -> view.printResults(store.findAlbumSong(query));
            case 'r' -> view.printResults(store.findSongRating(null));
            default -> view.invalid();
        }
    }

    /**
     * Process user command as an add command
     * 
     * @param <T>
     * @param command
     * @param query
     * @param store
     */
    private <T extends MusicStore> void add(String command, String query, T store) {
        if (!(store instanceof LibraryModel)) {
            switch (command.charAt(1)) {
                case 'a' -> {
                    store.findAlbumTitle(query).forEach(a -> {
                        currentUser.getLibrary().addAlbum(a);
                        musicStore.findSongAlbum(a.getAlbumTitle()).forEach(s -> currentUser.getLibrary().addSong(s));
                    });
                    if (!store.findAlbumTitle(query).isEmpty()) {
                        view.alert("Added album to Library.");
                    } else {
                        view.alert("Album not found");
                    }
                }
                case 's' -> {
                    store.findSongTitle(query).forEach(s -> currentUser.getLibrary().addSong(s, true));
                    if (!store.findSongTitle(query).isEmpty()) {
                        view.alert("Added song to Library.");
                    } else {
                        view.alert("Song not found");
                    }
                }
                default -> view.invalid();
            }
        } else {
            if (command.charAt(1) == 'p') {
                String[] playlistQuery = query.split(":");
                for (int i = 0; i < playlistQuery.length; i++) {
                    String s = playlistQuery[i];
                    playlistQuery[i] = s.trim();
                }
                boolean exists = !currentUser.getLibrary().findPlaylist(playlistQuery[0]).isEmpty();
                // If playlist exists, add song to playlist, else just create new playlist
                if (playlistQuery.length > 1) {
                    if (exists) {
                        try {
                            Song song = currentUser.getLibrary().findSongTitle(playlistQuery[1]).get(0);
                            currentUser.getLibrary().findPlaylist(playlistQuery[0]).get(0).addSong(song);
                            view.alert("Added song: " + song + "to" + playlistQuery[0] + ".");
                        } catch (IndexOutOfBoundsException e) {
                            view.error(e);
                        }
                    } else {
                        Playlist playlist = new Playlist(playlistQuery[0]);
                        try {
                            Song song = currentUser.getLibrary().findSongTitle(playlistQuery[1]).get(0);
                            playlist.addSong(song);
                            currentUser.getLibrary().addPlaylist(playlist);
                            view.alert("Created playlist: " + playlist.getName() + ", and added " + song.getTitle()
                                    + " to it.");
                        } catch (IndexOutOfBoundsException e) {
                            view.error(e);
                        }
                    }
                } else {
                    currentUser.getLibrary().addPlaylist(new Playlist(playlistQuery[0]));
                    view.alert("Added playlist: " + playlistQuery[0] + "to Library.");
                }
            } else {
                view.invalid();
            }
        }
    }

    /*
     * Process user command as a remove command
     * 
     * @param <T>
     * 
     * @param command
     * 
     * @param query
     * 
     * @param store
     */
    private <T extends MusicStore> void remove(String command, String query, T store) {
        if (command.charAt(1) == 'p' && store instanceof LibraryModel) {
            String[] playlistQuery = query.split(":");
            for (int i = 0; i < playlistQuery.length; i++) {
                String s = playlistQuery[i];
                playlistQuery[i] = s.trim();
            }
            Playlist playlist = currentUser.getLibrary().findPlaylist(playlistQuery[0]).get(0);
            if (playlistQuery.length > 1) {
                Song song = currentUser.getLibrary().findSongTitle(playlistQuery[1]).get(0);
                playlist.removeSong(song);
            } else {
                view.invalid();
            }
        } else if (command.charAt(1) == 'a' && store instanceof LibraryModel) {
            String albumTitle = query;
            try {
                Album album = currentUser.getLibrary().findAlbumTitle(albumTitle).get(0);
                currentUser.getLibrary().removeAlbum(album.getAlbumTitle());
                view.alert("Removed album: " + album.getAlbumTitle() + " from Library.");
            } catch (Exception e) {
                view.error(e);
            }
        } else if (command.charAt(1) == 's' && store instanceof LibraryModel) {
            String songTitle = query;
            try {
                Song song = currentUser.getLibrary().findSongTitle(songTitle).get(0);
                currentUser.getLibrary().removeSong(song.getTitle());
                view.alert("Removed song: " + song.getTitle() + " from Library.");
            } catch (Exception e) {
                view.error(e);
            }
        } else {
            view.invalid();
        }
    }

    /*
     * Process user command as a rate command
     * 
     * @param <T>
     * 
     * @param command
     * 
     * @param query
     * 
     * @param store
     */
    private <T extends MusicStore> void rate(String command, String query, T store) {
        if (command.charAt(1) == 's' && store instanceof LibraryModel) {
            String[] songQuery = query.split("\\s+");
            int rating = Integer.parseInt(songQuery[songQuery.length - 1]);
            String songTitle = "";
            for (int i = 0; i < songQuery.length - 1; i++) {
                songTitle = songQuery[i];
            }
            try {
                Song song = currentUser.getLibrary().findSongTitle(songTitle).get(0);
                if (songQuery.length > 1) {
                    song.setRating(rating);
                    view.alert("Set song: \"" + song.getTitle() + "\" rating to: " + rating + ".");
                    if (rating == 5) {
                        song.setFavorite(true);
                        view.alert("Added to favorites");
                    }

                } else {
                    view.invalid();
                }
            } catch (Exception e) {
                view.error(e);
            }
        } else {
            view.invalid();
        }
    }

    /*
     * Process user command as a favorite command
     * 
     * @param <T>
     * 
     * @param command
     * 
     * @param query
     * 
     * @param store
     */
    private <T extends MusicStore> void favorite(String command, String query, T store) {
        if (command.length() == 1) {
            view.printResults(currentUser.getLibrary().getFavorites());
        } else if (command.charAt(1) == 's' && store instanceof LibraryModel) {
            if (query.isEmpty()) {
                view.printResults(currentUser.getLibrary().getFavorites());
            } else
                try {
                    Song song = currentUser.getLibrary().findSongTitle(query).get(0);
                    song.setFavorite(!song.getFavorite());
                    if (song.getFavorite()) {
                        view.alert("Added to Favorites.");
                    } else {
                        view.alert("Removed from Favorites.");
                    }
                } catch (Exception e) {
                    view.error(e);
                }

        } else {
            view.invalid();
        }
    }

    private <T extends MusicStore> void play(String command, String query, T store) {
        // If not "ps" && lib, invalid
        if (command.length() != 2 || !(command.charAt(1) == 's' && store instanceof LibraryModel)) {
            view.invalid();
            return;
        }
        // If no query, print current
        if (query.isEmpty()) {
            Song song = currentUser.getLibrary().getNowPlaying();
            view.alert("Currently playing: " + (song == null ? "None" : song.getTitle()));
            return;
        }
        // Attempt to play input song
        String songTitle = query;
        boolean success = currentUser.getLibrary().playSong(songTitle);
        if (success) {
            view.alert("Now Playing: " + currentUser.getLibrary().getNowPlaying().getTitle());
        } else {
            view.alert("Song not found.");
        }
    }

    private <T extends MusicStore> void shuffle(String command, String query, T store) {
        if (command.length() != 2 || !(command.charAt(1) == 's' && store instanceof LibraryModel)) {
            view.invalid();
            return;
        }
        currentUser.getLibrary().shuffleSongs();
        view.alert("Shuffled songs.");
    }
}
