import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        Server server = new Server(8989, engine);
        server.start();
    }

}