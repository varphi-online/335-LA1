package main.view;

import java.util.ArrayList;
import java.util.Scanner;
import main.controller.Controller;
import main.model.Album;
import main.model.Playlist;
import main.model.Song;

public class View {
    private static Boolean mode = true; // true = musicStore, false = library

    public void UI(Controller controller) {
        Scanner main = new Scanner(System.in);
        clearScreen();
        printMenu();
        System.out.print("""
                Example:
                $ #                    - Search favorite songs
                $ *s Take It All -1    - Remove any rating of the song with title "Take It All"
                $ +p Chill Mix : Money - Add song w/ title "Money" to the "Chill Mix" playlist, or create it if it doesn't exist

                Mode: MusicStore
                >──────────────────────────────────────────────────────────────────────────────────────────────────────────────<""");
        System.out.print("\n\n> ");
        while (main.hasNextLine()) {
            String input = main.nextLine();
            if (input.isBlank()) {
                continue;
            }
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
                        default -> controller.handleInput(input, mode);
                    }
                }
            }
            System.out.print("> ");
        }
        main.close();
    }

    public void invalid() {
        System.out.println("Invalid Command.");
    }

    public void error(Exception e) {
        System.out.println("Error with Command: " + e.getMessage());
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
        System.out.println("");
        if (t.get(0) instanceof Song) {
            System.out.println("Songs:");

            System.out.println("│ " + format("Song Title", 41) + " │ " + format("Artist", 25) + " │ "
                    + format("Album Title", 30) + " │ "
                    + format("Genre", 6) + " │ " + format("Year", 6) + "│ Rating " + "│ Fav");
            System.out.println("├" + "─".repeat(42) + "┼" + "─".repeat(26) + "┼" + "─".repeat(31) + "┼" + "─".repeat(7)
                    + "┼" + "─".repeat(6) + "┼" + "─".repeat(8) + "┤");
            for (T item : t) {
                System.out.println(item);
            }
            System.out.println("└" + "─".repeat(42) + "┴" + "─".repeat(26) + "┴" + "─".repeat(31) + "┴" + "─".repeat(7)
                    + "┴" + "─".repeat(6) + "┴" + "─".repeat(8) + "┘");
        } else if (t.get(0) instanceof Album) {
            System.out.println("Albums:");
            System.out.println("│ " + format("Album Title", 30) + " │ " + format("Artist", 20) + " │ "
                    + format("Genre", 25) + " │ Year │");
            // System.out.println("├" + "─".repeat(31) +"┼" + "─".repeat(21) + "┼" +
            // "─".repeat(26) + "┼" + "─".repeat(6) + "┤");
            for (T item : t) {
                System.out.println(item);
            }
            System.out.println("└" + "─".repeat(87) + "┘");
        } else if (t.get(0) instanceof Playlist) {
            System.out.println("Playlists:\n");
            for (T item : t) {
                System.out.println(item);
            }
        }

    }

    public static void printMenu() {
        System.out.print("""
            ┌─────────────────────────────────────────────────────┬───────────────────────────────────────────────────────┐
            │                                                     │                                                       │
            │      Prefixes:                                      │      Commands:                                        │
            │      ? : Search                                     │      a <title>    -    Album by title                 │
            │      + : Add           (Exact)                      │      s <title>    -    Song by title                  │
            │      - : Remove        (Exact)                      │      A <artist>   -    Album by artist                │
            │      * : Rate          (Exact)                      │      S <artist>   -    Song by artist                 │
            │      # : Favorite      (Exact, Toggle, Search)      │      p <name> : <?song name>                          │
            │      h : Help                                       │          └ Playlist by name : (optional) w/ song      │
            │      q : Quit                                       │                                                       │
            │      m : Switch Mode   (MusicStore/Library)         │                                                       │
            │                                                     │                                                       │
            └─────────────────────────────────────────────────────┴───────────────────────────────────────────────────────┘\n\n""");
    }

    public static String format(String str, int n) {
        String s = str.substring(0, Math.min(n - 1, str.length()));
        int padding = Math.max(0, n - s.length() - 1);
        return s + " ".repeat(padding);
    }
}
