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

    public <T extends MusicStore> void command(String input, T store) {
        if (input.length() >= 2) {
            String[] inputArray = input.split(" ", 2);
            String command = inputArray[0];
            String[] query;
            if (inputArray.length > 1){
                query = inputArray[1].split("\\s+");
            } else {
                query = new String[] {""};
            }
            switch (command.charAt(0)) {
                case '?' -> {
                    switch (command.charAt(1)) {
                        case 'a' -> view.printResults(store.findAlbumTitle(String.join(" ", query)));
                        case 's' -> view.printResults(store.findSongTitle(String.join(" ", query)));
                        case 'A' -> view.printResults(store.findAlbumArtist(String.join(" ", query)));
                        case 'S' -> view.printResults(store.findSongArtist(String.join(" ", query)));
                        case 'p' -> {
                            if (store instanceof LibraryModel) {
                                view.printResults(libraryModel.findPlaylist(String.join(" ", query)));
                            }
                        }
                    }
                }
                case '+' -> {
                    if (store instanceof MusicStore) {
                        switch (command.charAt(1)) {
                            case 'a' -> store.findAlbumTitle(String.join(" ", query));
                            case 's' -> store.findSongTitle(String.join(" ", query));
                        }
                    }else{
                        switch (command.charAt(1)) {
                            case 'p' -> {
                                boolean exists = !libraryModel.findPlaylist(query[0]).isEmpty();
                                if(query.length > 1){
                                    if(exists){
                                        try{
                                        Song song = libraryModel.findSongTitle(query[1]).get(0);
                                        libraryModel.findPlaylist(query[0]).get(0).addSong(song);
                                        } catch (IndexOutOfBoundsException e){
                                            System.out.println("Song not found");
                                        }
                                    } else {
                                        Playlist playlist = new Playlist(query[0]);
                                        try{
                                            Song song = libraryModel.findSongTitle(query[1]).get(0);
                                            playlist.addSong(song);
                                            libraryModel.addPlaylist(playlist);
                                        } catch (IndexOutOfBoundsException e){
                                            System.out.println("Song not found");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                default -> throw new AssertionError();
            }
        }
    }

    public void library(String input) {
    }
}
