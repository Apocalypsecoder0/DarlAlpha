
import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private GameServer server;
    private PrintWriter out;
    private BufferedReader in;
    private String playerId;

    public ClientHandler(Socket socket, GameServer server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            playerId = in.readLine(); // First message should be player ID
            server.addClient(playerId, this);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                handleMessage(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void handleMessage(String message) {
        // Handle different message types
        if (message.startsWith("CHAT:")) {
            server.broadcastMessage("CHAT:" + playerId + ":" + message.substring(5), playerId);
        } else if (message.startsWith("MOVE:")) {
            server.broadcastMessage("MOVE:" + playerId + ":" + message.substring(5), playerId);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void cleanup() {
        try {
            server.removeClient(playerId);
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
