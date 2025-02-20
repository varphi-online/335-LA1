import java.util.ArrayList;
import java.util.Scanner;

public class View {
    private static Boolean mode = true; // true = musicStore, false = library

    public void UI(Controller controller) {
        Scanner main = new Scanner(System.in);
        clscr();
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
                    clscr();
                    printMenu();
                }
                default -> {
                    if (input.equals("clear")) {
                        clscr();
                    } else {
                        controller.handleInput(input, mode);
                    }
                }
            }
        }
    }

    public void invalid(){
        System.out.println("Invalid Command.");
    }

    public void error(Exception e){
        System.out.println("Error with Command: " + e.getMessage());
    }

    public void clscr() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public <T> void printResults(ArrayList<T> t) {
        if (t.isEmpty()) {
            System.out.println("No results found.");
            return;
        }
        for (T item : t) {
            System.out.println(item);
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
                        # : Favorite      (Exact, Toggle)
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
                        ?A Pink Floyd - Search for albums by Pink Floyd
                        *s @83923 -1 - Remove any rating of the song with id "83923"

                        >=============================================================================<

                        Enter Command:
                        """);
    }

}
