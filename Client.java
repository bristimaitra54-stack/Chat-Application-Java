import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);

            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(
                new InputStreamReader(System.in)
            );

            System.out.print("Enter your name: ");
            String name = userInput.readLine();

            // Receive messages
            new Thread(() -> {
                try {
                    String msg;
                    while((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch(Exception e) {}
            }).start();

            // Send messages
            String msg;
            while((msg = userInput.readLine()) != null) {
                out.println(name + ": " + msg);
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
