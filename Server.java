import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    static Set<PrintWriter> clients = new HashSet<>();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1234);
            System.out.println("Server started...");

            while(true) {
                Socket socket = server.accept();
                System.out.println("New client connected!");

                new ClientHandler(socket).start();
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    static class ClientHandler extends Thread {
        Socket socket;
        PrintWriter out;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );

                out = new PrintWriter(socket.getOutputStream(), true);

                synchronized(clients) {
                    clients.add(out);
                }

                String msg;
                while((msg = in.readLine()) != null) {
                    System.out.println("Message: " + msg);

                    synchronized(clients) {
                        for(PrintWriter writer : clients) {
                            writer.println(msg);
                        }
                    }
                }

            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }
}