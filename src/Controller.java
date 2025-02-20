import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Controller {
    private static MusicStore musicStore;
    private static LibraryModel libraryModel;
    private static View view;

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Music Store!");
        Controller controller = new Controller();
        libraryModel = new LibraryModel();
        view = new View();
        view.UI(controller);
    }

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

    private void parseAlbum(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String[] albumInfo = line.split(",");
        String albumTitle = albumInfo[0];
        String artist = albumInfo[1];
        String genre = albumInfo[2];
        int year = Integer.parseInt(albumInfo[3]);
        Album album = new Album(albumTitle, artist, genre, year);
        musicStore.albums.add(album);
        while ((line = br.readLine()) != null) {
            String title = line;
            Song song = new Song(title, album);
            musicStore.songs.add(song);
        }
        br.close();
    }

    public MusicStore getMusicStore() {
        return musicStore;
    }

    public void handleInput(String input, Boolean mode) {
        if (mode) {
            command(input, musicStore);
        } else {
            command(input, libraryModel);
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public <T extends MusicStore> void command(String input, T store) {
        if (input.length() >= 2) {
            String[] inputArray = input.split(" ", 2);
            String command = inputArray[0];
            String query;
            if (inputArray.length > 1) {
                query = inputArray[1];
            } else {
                query = "";
            }
            switch (command.charAt(0)) {
                case '?' -> {
                    switch (command.charAt(1)) {
                        case 'a' -> view.printResults(store.findAlbumTitle(query));
                        case 's' -> view.printResults(store.findSongTitle(query));
                        case 'A' -> view.printResults(store.findAlbumArtist(query));
                        case 'S' -> view.printResults(store.findSongArtist(query));
                        case 'p' -> {
                            if (store instanceof LibraryModel) {
                                view.printResults(libraryModel.findPlaylist(query));
                            }
                        }
                    }
                }
                case '+' -> {
                    if (!(store instanceof LibraryModel)) {
                        switch (command.charAt(1)) {
                            case 'a' -> {
                                store.findAlbumTitle(query).forEach(a -> {
                                    libraryModel.addAlbum(a);
                                    musicStore.findSongAlbum(a.getAlbumTitle()).forEach(s -> libraryModel.addSong(s));
                                });
                            }
                            case 's' -> store.findSongTitle(query).forEach(s -> libraryModel.addSong(s));
                        }
                    } else {
                        if (command.charAt(1) == 'p') {
                            String[] playlistQuery = query.split(":");
                            for (int i = 0; i < playlistQuery.length; i++) {
                                String s = playlistQuery[i];
                                playlistQuery[i] = s.trim();
                            }
                            boolean exists = !libraryModel.findPlaylist(playlistQuery[0]).isEmpty();
                            if (playlistQuery.length > 1 && query.contains(":")) {
                                if (exists) {
                                    try {
                                        Song song = libraryModel.findSongTitle(playlistQuery[1]).get(0);
                                        libraryModel.findPlaylist(playlistQuery[0]).get(0).addSong(song);
                                    } catch (IndexOutOfBoundsException e) {
                                        System.out.println("Song not found");
                                    }
                                } else {
                                    Playlist playlist = new Playlist(playlistQuery[0]);
                                    try {
                                        Song song = libraryModel.findSongTitle(playlistQuery[1]).get(0);
                                        playlist.addSong(song);
                                        libraryModel.addPlaylist(playlist);
                                    } catch (IndexOutOfBoundsException e) {
                                        System.out.println("Song not found");
                                    }
                                }
                            } else {
                                libraryModel.addPlaylist(new Playlist(playlistQuery[0]));
                            }

                        }
                    }
                }
                case '-' -> {
                    if (command.charAt(1) == 'p') {
                        if (store instanceof LibraryModel) {
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
                    } else {
                        view.invalid();
                    }
                }
                case '*' -> {
                    if (command.charAt(1) == 's') {
                        if (store instanceof LibraryModel) {
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
                                    if (rating == 5) {
                                        song.setFavorite(true);
                                    }
                                } else {
                                    view.invalid();
                                }
                            } catch (Exception e) {
                                view.error(e);
                            }
                        }
                    } else {
                        view.invalid();
                    }
                }
                case '#' -> {
                    if (command.charAt(1) == 's') {
                        if (store instanceof LibraryModel) {
                            try {
                                Song song = libraryModel.findSongTitle(query).get(0);
                                song.setFavorite(!song.getFavorite());
                            } catch (Exception e) {
                                view.error(e);
                            }
                        }
                    } else {
                        view.invalid();
                    }
                }
                default -> throw new AssertionError();
            }
        }
    }

    public void library(String input) {
    }
}
