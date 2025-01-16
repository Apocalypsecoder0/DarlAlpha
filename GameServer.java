
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class GameServer {
    private static final int PORT = 8080;
    private ServerSocket serverSocket;
    private ConcurrentHashMap<String, ClientHandler> connectedClients;
    private Game gameInstance;
    private Map<String, String> userAccounts;

    public GameServer() {
        this.connectedClients = new ConcurrentHashMap<>();
        this.gameInstance = new Game();
        this.userAccounts = new ConcurrentHashMap<>();
    }

    public boolean login(String username, String password) {
        if (userAccounts.containsKey(username) && userAccounts.get(username).equals(password)) {
            System.out.println("User logged in: " + username);
            return true;
        }
        return false;
    }

    public boolean register(String username, String password) {
        if (!userAccounts.containsKey(username)) {
            userAccounts.put(username, password);
            System.out.println("New user registered: " + username);
            return true;
        }
        return false;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT));
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message, String excludePlayer) {
        connectedClients.forEach((playerId, handler) -> {
            if (!playerId.equals(excludePlayer)) {
                handler.sendMessage(message);
            }
        });
    }

    public void addClient(String playerId, ClientHandler handler) {
        connectedClients.put(playerId, handler);
        broadcastMessage("Player " + playerId + " joined the game", playerId);
    }

    public void removeClient(String playerId) {
        connectedClients.remove(playerId);
        broadcastMessage("Player " + playerId + " left the game", playerId);
    }

    public Game getGameInstance() {
        return gameInstance;
    }
}
