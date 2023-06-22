import java.io.*;
import java.net.Socket;

public class Client {

    private static final int PORT = 8989;
    private static final String HOST = "localhost";
    public static void main(String[] args) {


        try (Socket clientSocket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Введите слово: ");
            String word = reader.readLine();
            out.println(word);
            System.out.println("Ответ сервера: " + in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
