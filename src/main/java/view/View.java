package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import controller.Controller;
import model.Album;
import model.Playlist;
import model.Song;

public class View {
    private static Boolean mode = true; // true = musicStore, false = library
    private static Boolean loggedIn = false;

    /**
     * Main UI loop
     * @param controller
     */
    public void UI(Controller controller) {
        Scanner main = new Scanner(System.in);
        System.out.println("Welcome to the Music Player! To login, type 'login <username> <password>', or type 'create <username> <password>' to create a new account.");
        System.out.print("> ");
        while (main.hasNextLine()) {
            String input = main.nextLine();
            if (input.isBlank()) {
                continue;
            }
            if (loggedIn){
                mainLoop(input, controller);
            } else {
                onboard(input, controller);
            }
        }
        main.close();
    }

    public void mainLoop(String input, Controller controller) {
        switch (input.charAt(0)) {
            case 'h' -> printMenu();
            case 'q' -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            case 'm' -> {
                mode = !mode;
                System.out.println("Mode switched to " + (mode ? "MusicStore" : "Library"));
            }
            default -> {
                switch (input) {
                    case "clear" -> clearScreen();
                    case "help" -> printMenu();
                    case "logout" ->{
                        loggedIn = false;
                        controller.logout();
                    }
                    default -> controller.handleInput(input, mode);
                }
            }
        }
        System.out.print("> ");
    }

    public void onboard(String input, Controller controller){
        String[] split = input.split(" ");
        if (split.length != 3){
            System.out.println("Invalid input. Please try again.");
        } else {
            switch (split[0]){
                case "login" -> {
                    loggedIn = controller.login(split[1], split[2]);
                }
                case "create" -> {
                    loggedIn = controller.createUser(split[1], split[2]);
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }

        if (loggedIn){
            clearScreen();
            printMenu();
            System.out
            .print("""
        		Example:
                $ #                    - Search favorite songs
                $ *s Take It All -1    - Remove any rating of the song with title "Take It All"
                $ +p Chill Mix : Money - Add song w/ title "Money" to the "Chill Mix" playlist, or create it if it doesn't exist

                Mode: MusicStore
                >──────────────────────────────────────────────────────────────────────────────────────────────────────────────<""");
            System.out.print("\n\n> ");
        } else {
            System.out.print("> ");
        }
    }

    public void invalid() {
        System.out.println("Invalid Command.");
    }

    public void error(Exception e) {
        if (e instanceof IndexOutOfBoundsException) {
            System.out.println("Not found.");
        } else
            System.out.println("Error with Command: " + e.getMessage());
    }

    public void alert(String message) {
        System.err.println(message);
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public <T> void printResults(ArrayList<T> t) {
        if (t.isEmpty()) {
            System.out.println("No results found.");
            return;
        }
        String toPrint = t.stream().map(Object::toString).collect(Collectors.joining("\n"));
        if (t.get(0) instanceof Song) {
            System.out.println(
                    """
                            Songs:
                            │ Song Title                               │ Artist                   │ Album Title                   │ Genre │ Year │ Rating │ Fav
                            ├──────────────────────────────────────────┼──────────────────────────┼───────────────────────────────┼───────┼──────┼────────┤
                            %s
                            └──────────────────────────────────────────┴──────────────────────────┴───────────────────────────────┴───────┴──────┴────────┘
                            """
                            .formatted(toPrint));
        } else if (t.get(0) instanceof Album) {
            System.out.println(
                    """
                            Albums:
                            │ Album Title                   │ Artist              │ Genre                    │ Year │
                            %s
                            └───────────────────────────────────────────────────────────────────────────────────────┘
                            """.formatted(toPrint));
        } else if (t.get(0) instanceof Playlist) {
            System.out.println("Playlists:\n" + toPrint);
        }

    }

    public static void printMenu() {
        System.out
                .print("""
                        ┌─────────────────────────────────────────────────────┬───────────────────────────────────────────────────────┐
                        │                                                     │                                                       │
                        │      Prefixes:                                      │      Commands:                                        │
                        │      ? : Search                                     │      a <title>    -    Album by title                 │
                        │      + : Add           (Exact)                      │      s <title>    -    Song by title                  │
                        │      - : Remove        (Exact)                      │      A <artist>   -    Album by artist                │
                        │      * : Rate          (Exact)                      │      S <artist>   -    Song by artist                 │
                        │      # : Favorite      (Exact, Toggle, Search)      │      n            -    Recent Played Songs            │
                        │      h : Help                                       │      N            -    Top Played Songs               │
                        │      q : Quit                                       │      r            -    By Rating                      │
                        │      m : Switch Mode   (MusicStore/Library)         │      p <name> : <?song name>                          │
                        │      p : Play                                       │          └ Playlist by name : (optional) w/ song      │
                        │      & : Shuffle                                    │      g            -    Song by Genre                  │
                        │                                                     │      f            -    Album by Song                  │
                        │                                                     │                                                       │
                        └─────────────────────────────────────────────────────┴───────────────────────────────────────────────────────┘\n\n""");
    }

}
