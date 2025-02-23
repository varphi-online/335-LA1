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
                    clearScreen();
                    printMenu();
                }
                default -> {
                    if (input.equals("clear")) {
                        clearScreen();
                        System.out.println("Enter Command:");
                    } else {
                        controller.handleInput(input, mode);
                    }
                }
            }
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
            System.out.println("├" + "─".repeat(42) +"┼" + "─".repeat(26) + "┼" + "─".repeat(31) + "┼" + "─".repeat(7) + "┼" + "─".repeat(6) + "┼" + "─".repeat(8) + "┤");
            for (T item : t) {
                System.out.println(item);
            }
            System.out.println("└" + "─".repeat(42) + "┴" + "─".repeat(26) + "┴" + "─".repeat(31) + "┴" + "─".repeat(7)
                    + "┴" + "─".repeat(6) + "┴" + "─".repeat(8) + "┘");
        } else if (t.get(0) instanceof Album) {
            System.out.println("Albums:");
            System.out.println("│ " + format("Album Title", 30) + " │ " + format("Artist", 20) + " │ "
                    + format("Genre", 25) + " │ Year │");
            System.out.println("├" + "─".repeat(31) +"┼" + "─".repeat(21) + "┼" + "─".repeat(26) + "┼" + "─".repeat(6) + "┤");
            for (T item : t) {
                System.out.println(item);
            }
            System.out.println("└" + "─".repeat(31) + "┴" + "─".repeat(21) + "┴" + "─".repeat(26) + "┴" + "──────┘");
        } else if (t.get(0) instanceof Playlist) {
            System.out.println("Playlists:\n");
            for (T item : t) {
                System.out.println(item);
            }
        }

    }

    public static void printMenu() {
        System.out.print(
                "Mode: " + (mode ? "MusicStore" : "Library") + "\n\n" + """
                        Prefixes:
                        ? : Search
                        + : Add           (Exact)
                        - : Remove        (Exact)
                        * : Rate          (Exact)
                        # : Favorite      (Exact, Toggle, Search)
                        h : Help
                        q : Quit
                        m : Switch Mode   (MusicStore/Library)

                        Commands:
                        a <title>    -    Album by title
                        s <title>    -    Song by title/id
                        A <artist>   -    Album by artist
                        S <artist>   -    Song by artist
                        p <name> : <?song name/id> - Playlist by name : (optionally) with song

                        Note: Songs may be referenced by name or @id when adding to a playlist/library.

                        Example:
                        $ # - Search favorite songs
                        $ *s @83923 -1 - Remove any rating of the song with id "83923"

                        >=============================================================================<

                        Enter Command:
                        """);
    }

    public static String format(String str, int n) {
        String s = str.substring(0, Math.min(n - 1, str.length()));
        int padding = Math.max(0, n - s.length() - 1);
        return s + " ".repeat(padding);
    }
}
