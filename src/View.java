import java.io.IOException;
public class View {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Music Store!");
        Controller controller = new Controller();
        System.out.println(controller.getMusicStore().toString());
    }
}
