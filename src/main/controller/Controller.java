package main.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import main.model.Album;
import main.model.LibraryModel;
import main.model.MusicStore;
import main.model.Playlist;
import main.model.Song;
import main.view.View;

/**
 * Controller class for the Music Store application
 * Creates View and Models, linking View inputs back 
 * to itself for processing of Model(s)
 */
public class Controller {
    private static MusicStore musicStore;
    private static LibraryModel libraryModel;
    private static View view;

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        libraryModel = new LibraryModel();
        view = new View();
        view.UI(controller);
    }

    /**
     * Constructor for Controller
     * Initializes the MusicStore and LibraryModel from the albums.txt file
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
    }

    /**
     * Parses provided album file and adds it to the MusicStore
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

    /*
     * Handles input from the View, dispatching 
     * operation to the selected store
     * @param input
     * @param mode
     */
    public void handleInput(String input, Boolean mode) {
        if (mode) {
            dispatchCommand(input, musicStore);
        } else {
            dispatchCommand(input, libraryModel);
        }
    }

    /**
     * Dispatches the command to the appropriate 
     * method, operating on the correct store T
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
                default -> view.invalid();
            }
        } else {
            if (input.equals("#")) {
                favorite("#", "", store);
            } else {
                view.invalid();
            }
        }
    }

    /**
     * Process user command as a search command
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
                    view.printResults(libraryModel.findPlaylist(query));
                } else {
                    view.invalid();
                }
            }
            case 'n' -> {
                if (store instanceof LibraryModel) {
                    view.printResults(libraryModel.getMostRecentSongs());
                } else {
                    view.invalid();
                }
            }
            case 'N' -> {
                if (store instanceof LibraryModel) {
                    view.printResults(libraryModel.getTopPlayedSongs());
                } else {
                    view.invalid();
                }
            }
            case 'r' -> view.printResults(store.findSongRating(null));
            default -> view.invalid();
        }
    }

    /**
     * Process user command as an add command
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
                        libraryModel.addAlbum(a);
                        musicStore.findSongAlbum(a.getAlbumTitle()).forEach(s -> libraryModel.addSong(s));
                    });
                    if (!store.findAlbumTitle(query).isEmpty()) {
                        view.alert("Added album to Library.");
                    } else {
                        view.alert("Album not found");
                    }
                }
                case 's' -> {
                    store.findSongTitle(query).forEach(s -> libraryModel.addSong(s));
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
                boolean exists = !libraryModel.findPlaylist(playlistQuery[0]).isEmpty();
                // If playlist exists, add song to playlist, else just create new playlist
                if (playlistQuery.length > 1) {
                    if (exists) {
                        try {
                            Song song = libraryModel.findSongTitle(playlistQuery[1]).get(0);
                            libraryModel.findPlaylist(playlistQuery[0]).get(0).addSong(song);
                            view.alert("Added song: "+song+"to"+playlistQuery[0]+".");
                        } catch (IndexOutOfBoundsException e) {
                            view.error(e);
                        }
                    } else {
                        Playlist playlist = new Playlist(playlistQuery[0]);
                        try {
                            Song song = libraryModel.findSongTitle(playlistQuery[1]).get(0);
                            playlist.addSong(song);
                            libraryModel.addPlaylist(playlist);
                            view.alert("Created playlist: "+playlist.getName()+", and added " + song.getTitle()+" to it.");
                        } catch (IndexOutOfBoundsException e) {
                            view.error(e);
                        }
                    }
                } else {
                    libraryModel.addPlaylist(new Playlist(playlistQuery[0]));
                    view.alert("Added playlist: "+playlistQuery[0]+"to Library.");
                }
            } else {
                view.invalid();
            }
        }
    }

    /*
     * Process user command as a remove command
     * @param <T>
     * @param command
     * @param query
     * @param store
     */
    private <T extends MusicStore> void remove(String command, String query, T store) {
        if (command.charAt(1) == 'p' && store instanceof LibraryModel) {
            String[] playlistQuery = query.split(":");
            for (int i = 0; i < playlistQuery.length; i++) {
                String s = playlistQuery[i];
                playlistQuery[i] = s.trim();
            }
            Playlist playlist = libraryModel.findPlaylist(playlistQuery[0]).get(0);
            if (playlistQuery.length > 1) {
                Song song = libraryModel.findSongTitle(playlistQuery[1]).get(0);
                playlist.removeSong(song);
            } else {
                view.invalid();
            }
        } 
        else if (command.charAt(1) == 'a' && store instanceof LibraryModel) {
            String albumTitle = query;
            try {
                Album album = libraryModel.findAlbumTitle(albumTitle).get(0);
                libraryModel.removeAlbum(album.getAlbumTitle());
                view.alert("Removed album: "+album.getAlbumTitle()+" from Library.");
            } catch (Exception e) {
                view.error(e);
            }
        }
        else if (command.charAt(1) == 's' && store instanceof LibraryModel) {
            String songTitle = query;
            try {
                Song song = libraryModel.findSongTitle(songTitle).get(0);
                libraryModel.removeSong(song.getTitle());
                view.alert("Removed song: "+song.getTitle()+" from Library.");
            } catch (Exception e) {
                view.error(e);
            }
        }
        else {
            view.invalid();
        }
    }

    /*
     * Process user command as a rate command
     * @param <T>
     * @param command
     * @param query
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
                Song song = libraryModel.findSongTitle(songTitle).get(0);
                if (songQuery.length > 1) {
                    song.setRating(rating);
                    view.alert("Set song: \""+song.getTitle()+"\" rating to: "+rating+".");
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
     * @param <T>
     * @param command
     * @param query
     * @param store
     */
    private <T extends MusicStore> void favorite(String command, String query, T store) {
        if (command.length() == 1) {
            view.printResults(libraryModel.getFavorites());
        } else if (command.charAt(1) == 's' && store instanceof LibraryModel) {
            if (query.isEmpty()) {
                view.printResults(libraryModel.getFavorites());
            } else
                try {
                    Song song = libraryModel.findSongTitle(query).get(0);
                    song.setFavorite(!song.getFavorite());
                    if (song.getFavorite()){
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
            Song song = libraryModel.getNowPlaying();
            view.alert("Currently playing: " + (song == null ? "None" : song.getTitle()));
            return;
        }
        // Attempt to play input song
        String songTitle = query;
        boolean success = libraryModel.playSong(songTitle);
        if (success) { 
            view.alert("Now Playing: " + libraryModel.getNowPlaying().getTitle());
        } else {
            view.alert("Song not found.");
        }
    }
}
