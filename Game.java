import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Game {
    private Player player;
    private Universe universe;
    private Economy economy;
    private ResearchSystem research;
    private CombatSystem combat;
    private MissionSystem missions;
    private AllianceSystem alliances;
    private DungeonSystem dungeons;
    private GoddessSystem goddessSystem;
    private MessageSystem messageSystem;
    private ServerSocket serverSocket;
    private Map<String, CelestialBody> celestialBodies;
    private DungeonFinder dungeonFinder;
    private AudioSystem audioSystem;
    private SaveSystem saveSystem;
    private PartySystem partySystem;
    private ExpansionSystem expansionSystem;
    private Store store;
    private CodexSystem codexSystem;
    private Tutorial tutorial;
    private Empire playerEmpire;
    private AnnouncementSystem announcementSystem;
    private EmpireSkillSystem empireSkillSystem;
    private ZoneGuideSystem zoneGuideSystem;
    private PrestigeSystem prestigeSystem;
    private AscensionSystem ascensionSystem;
    private EmpireTitleSystem empireTitleSystem;
    private Battleground battleground;
    private BattlefieldRaid battlefieldRaid;
    private Ship ship;

    public Game() {
        System.out.println("Dark Alpha Engine v1.0.0");
        System.out.println("Developer: Apocalypsecode0");
        System.out.println("\\nDebug: Initializing game systems...");
        initializeSystems();
        System.out.println("Debug: Initializing player...");
        initializePlayer();
        System.out.println("Debug: Initializing celestial bodies...");
        this.celestialBodies = initializeCelestialBodies();
        System.out.println("Debug: Game initialization complete");
    }

    private void initializeSystems() {
        this.messageSystem = new MessageSystem();
        this.announcementSystem = new AnnouncementSystem();
        this.empireSkillSystem = new EmpireSkillSystem();
        this.battleground = new Battleground();
        this.battlefieldRaid = new BattlefieldRaid();
        this.universe = new Universe();
        this.economy = new Economy();
        this.research = new ResearchSystem();
        this.combat = new CombatSystem();
        this.missions = new MissionSystem();
        this.alliances = new AllianceSystem();
        this.dungeons = new DungeonSystem();
        this.dungeonFinder = new DungeonFinder();
        this.goddessSystem = new GoddessSystem();
        this.audioSystem = new AudioSystem();
        this.saveSystem = new SaveSystem();
        this.partySystem = new PartySystem();
        this.expansionSystem = new ExpansionSystem();
        this.store = new Store();
        this.codexSystem = new CodexSystem();
        this.zoneGuideSystem = new ZoneGuideSystem();
        this.prestigeSystem = new PrestigeSystem();
        this.ascensionSystem = new AscensionSystem();
        this.empireTitleSystem = new EmpireTitleSystem();
    }

    private Map<String, CelestialBody> initializeCelestialBodies() {
        Map<String, CelestialBody> bodies = new HashMap<>();
        bodies.put("Sun", new CelestialBody("Sun", new Coordinates(0, 0), 100, "Star"));
        return bodies;
    }

    private void initializePlayer() {
        this.player = new Player();
        this.ship = new Ship(new Position(new Coordinates3D(0, 0, 0)));
        this.tutorial = new Tutorial(player);
        this.playerEmpire = new Empire();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("0.0.0.0", 12345));
            System.out.println("Server started on port 12345");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler handler = new ClientHandler(clientSocket, this);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() { return player; }
    public Universe getUniverse() { return universe; }
    public void setUniverse(Universe universe) { this.universe = universe; }
    public SaveSystem getSaveSystem() { return saveSystem; }
    public AudioSystem getAudioSystem() { return audioSystem; }
    public Ship getShip() { return ship; }
    public MessageSystem getMessageSystem() { return messageSystem; }
    public CodexSystem getCodexSystem() { return codexSystem; }
    public DungeonFinder getDungeonFinder() { return dungeonFinder; }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Game game;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientHandler(Socket clientSocket, Game game) {
        this.clientSocket = clientSocket;
        this.game = game;
        try {
            this.dataInputStream = new DataInputStream(clientSocket.getInputStream());
            this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = dataInputStream.readUTF();
                System.out.println("Client message: " + message);
                processClientMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processClientMessage(String message) {
        String[] parts = message.split(":");
        if (parts.length == 2) {
            String command = parts[0];
            String data = parts[1];
            switch (command) {
                case "getMessage":
                    sendMessage("Server message: Hello from the server!");
                    break;
                case "playSound":
                    game.getAudioSystem().playSound(data);
                    break;
                case "saveGame":
                    try {
                        int slot = Integer.parseInt(data);
                        game.getSaveSystem().saveGame(new SaveSystem.GameData(100,1,"Player1"), slot);
                    } catch (NumberFormatException e) {
                        sendMessage("Invalid save slot");
                    }
                    break;
                case "getDungeonList":
                    sendMessage("dungeons:dungeon1,dungeon2");
                    break;
                default:
                    sendMessage("Unknown command");
            }
        } else {
            sendMessage("Invalid command format");
        }
    }


    private void sendMessage(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class GameTest{
    public static void main(String[] args) {
        Game game = new Game();
        game.messageSystem.printMessage("Game Started!");
        game.audioSystem.playMusic("backgroundMusic.mp3");
        SaveSystem.GameData gameData = new SaveSystem.GameData(100,1,"Player1");
        game.getSaveSystem().saveGame(gameData,0);
        SaveSystem.GameData loadedData = game.getSaveSystem().loadGame(0);
        if(loadedData != null){
            System.out.println("Loaded Data: Health = " + loadedData.playerHealth + ", Level = " + loadedData.playerLevel + ", Name = " + loadedData.playerName);
        }
        game.getShip().getImpulseDrive().activateImpulse(new Coordinates3D(10,20,30));
        game.getDungeonFinder().searchForDungeons("level: 5");
        game.codexSystem.addEntry("New entry");
        game.start();
    }
}

class EngineInfo {
    public static final String ENGINE_NAME = "MyGameEngine";
    public static final String VERSION = "1.0";
    public static final String AUTHOR = "Your Name";

    private static String loadLogo() {
        return "  _.--\"\"--._\\n" +
               " .'          '.\\n" +
               "/   O      O   \\\\n" +
               "|    \\  ^  /    |\\n" +
               "\\     `----'     /\\n" +
               " `. _______ .' \\n" +
               "   //_____\\\\ \\n" +
               "  (( ____ ))\\n" +
               "   `-----'";
    }
}