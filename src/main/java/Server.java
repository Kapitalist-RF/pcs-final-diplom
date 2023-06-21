import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private Gson gson = new Gson();
    private int port;
    private BooleanSearchEngine engine;

    public Server(int port, BooleanSearchEngine engine) {
        this.port = port;
        this.engine = engine;
    }

    public void start() {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(port);) {
                System.out.println("Starting server at " + port + "...");
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    System.out.println("IN");
                    String word = in.readLine();
                    List<PageEntry> list = engine.search(word);
                    System.out.println("OUT");
                    out.println(gson.toJson(list));
                    System.out.println("End server");

                }

            } catch (IOException e) {
                System.out.println("Не могу стартовать сервер");
                e.printStackTrace();
            }
        }
    }
}