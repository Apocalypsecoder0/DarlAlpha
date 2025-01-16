package main.java;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Debug: Starting game initialization...");
            System.out.println("Debug: Creating game instance...");
            Game game = new Game();
            System.out.println("Debug: Game systems initialized");
            System.out.println("Debug: Starting game server...");
            game.start();
            System.out.println("Debug: Game server started successfully");
        } catch (Exception e) {
            System.err.println("Debug: Critical error occurred - " + e.getMessage());
            System.err.println("Debug: Stack trace:");
            e.printStackTrace();
        }
    }
}

class Ship {
    private String name;
    private String role;
    private int health;
    private int shields;
    private int attack;
    private int level;
    private int upgradeCost;
    private Position position;

    public static enum Position {
        FRONT, MIDDLE, BACK
    }

    public Ship(String name) {
        this.name = name;
        this.level = 1;
        initializeShipStats();
    }

    private void initializeShipStats() {
        switch(name.toLowerCase()) {
            case "destroyer":
                role = "Assault";
                health = 80;
                shields = 40;
                attack = 35;
                position = Position.FRONT;
                break;
            case "battleship":
                role = "Tank";
                health = 120;
                shields = 60;
                attack = 25;
                position = Position.FRONT;
                break;
            case "cruiser":
                role = "Support";
                health = 90;
                shields = 50;
                attack = 20;
                position = Position.MIDDLE;
                break;
            case "carrier":
                role = "Command";
                health = 150;
                shields = 80;
                attack = 15;
                position = Position.BACK;
                break;
            case "interceptor":
                role = "Scout";
                health = 60;
                shields = 30;
                attack = 40;
                position = Position.MIDDLE;
                break;
            case "dreadnought":
                role = "Capital";
                health = 200;
                shields = 100;
                attack = 45;
                position = Position.FRONT;
                break;
            case "support":
                role = "Healer";
                health = 70;
                shields = 45;
                attack = 10;
                position = Position.BACK;
                break;
            default:
                role = "Scout";
                health = 100;
                shields = 50;
                attack = 25;
                position = Position.BACK;
        }
        upgradeCost = 1000;
    }

    public boolean canHeal() {
        return role.equals("Healer");
    }

    public int getHealAmount() {
        return canHeal() ? 20 : 0;
    }

    public boolean upgrade(Resources resources) {
        if (resources.getCredits() >= upgradeCost) {
            resources.spendCredits(upgradeCost);
            level++;
            health += 20;
            shields += 10;
            attack += 5;
            upgradeCost = (int)(upgradeCost * 1.5);
            return true;
        }
        return false;
    }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getShields() { return shields; }
    public void setShields(int shields) { this.shields = shields; }
    public int getAttack() { return attack; }
    public String getName() { return name; }
    public Position getPosition() { return position; }
}

// Universe structure
class Universe {
    private List<Galaxy> galaxies;
    private Map<String, GalaxyType> galaxyTypes;
    private long seed;
    private UniverseGenerator generator;

    public Universe() {
        this.seed = System.currentTimeMillis();
        this.generator = new UniverseGenerator(seed);
        this.galaxies = new ArrayList<>();
        initializeGalaxyTypes();
        initializeGalaxies();
    }

    private void initializeGalaxyTypes() {
        galaxyTypes = new HashMap<>();
        galaxyTypes.put("INDUSTRIAL", new GalaxyType("Industrial", 2.0, 0.5, 1.0));
        galaxyTypes.put("MINING", new GalaxyType("Mining", 0.5, 2.0, 1.0));
        galaxyTypes.put("RESEARCH", new GalaxyType("Research", 1.0, 1.0, 2.0));
        galaxyTypes.put("FRONTIER", new GalaxyType("Frontier", 1.5, 1.5, 1.5));
        galaxyTypes.put("CONTESTED", new GalaxyType("Contested", 3.0, 3.0, 3.0));
    }

    private void initializeGalaxies() {
        this.galaxies = generator.generateGalaxies();
    }

    public Galaxy findGalaxyByCoordinates(Coordinates coords) {
        return galaxies.stream()
            .min((g1, g2) -> Double.compare(
                g1.getCoordinates().distanceTo(coords),
                g2.getCoordinates().distanceTo(coords)))
            .orElse(null);
    }
}

class GalaxyType {
    private String name;
    private double resourceMultiplier;
    private double miningMultiplier;
    private double researchMultiplier;

    public GalaxyType(String name, double resourceMult, double miningMult, double researchMult) {
        this.name = name;
        this.resourceMultiplier = resourceMult;
        this.miningMultiplier = miningMult;
        this.researchMultiplier = researchMult;
    }

    public double getResourceMultiplier() { return resourceMultiplier; }
    public double getMiningMultiplier() { return miningMultiplier; }
    public double getResearchMultiplier() { return researchMultiplier; }
}

class Galaxy {
    private String name;
    private String type;
    private List<Sector> sectors;
    private Coordinates coordinates;
    private double resourceMultiplier;
    private double riskMultiplier;
    private static final int SECTOR_GRID_SIZE = 10;

    public Galaxy(String name, String type, Coordinates coords) {
        this.name = name;
        this.type = type;
        this.coordinates = coords;
        this.sectors = new ArrayList<>();
        initializeSectors();
    }

    private void initializeSectors() {
        for (int x = 0; x < SECTOR_GRID_SIZE; x++) {
            for (int y = 0; y < SECTOR_GRID_SIZE; y++) {
                sectors.add(new Sector(x, y, calculateRiskLevel(x, y)));
            }
        }
    }

    private int calculateRiskLevel(int x, int y) {
        // Risk increases with distance from center
        int centerX = SECTOR_GRID_SIZE / 2;
        int centerY = SECTOR_GRID_SIZE / 2;
        double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        return (int)(distance * 1.5);
    }

    public Coordinates getCoordinates() { return coordinates; }
    public void setResourceMultiplier(double resourceMultiplier) { this.resourceMultiplier = resourceMultiplier; }
    public void setRiskMultiplier(double riskMultiplier) { this.riskMultiplier = riskMultiplier; }
}

class Coordinates {
    private double x, y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Coordinates other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
}

class Sector {
    private Coordinates coordinates;
    private List<Resource> resources;
    private int riskLevel;
    private boolean explored;
    private Map<String, Double> resourceDistribution;

    public Sector(int x, int y, int riskLevel) {
        this.coordinates = new Coordinates(x, y);
        this.riskLevel = riskLevel;
        this.explored = false;
        this.resources = new ArrayList<>();
        this.resourceDistribution = new HashMap<>();
        generateResources();
    }

    private void generateResources() {
        resourceDistribution.put("minerals", 20.0 + (riskLevel * 5));
        resourceDistribution.put("energy", 15.0 + (riskLevel * 3));
        resourceDistribution.put("tech", 10.0 + (riskLevel * 7));
    }

    public double getResourceAmount(String type) {
        return resourceDistribution.getOrDefault(type, 0.0) * (explored ? 1 : 0.5);
    }

    public int getRiskLevel() { return riskLevel; }
    public boolean isExplored() { return explored; }
    public void explore() { this.explored = true; }
}

// Resource management
class Resources {
    private int energy;
    private int minerals;
    private int techPoints;
    private int credits;

    public Resources() {
        this.energy = 1000;
        this.minerals = 500;
        this.techPoints = 0;
        this.credits = 1000;
    }

    public void mine(Sector sector, Stats stats) {
        minerals += 10 * stats.getMiningEfficiency();
        energy -= 5;
    }

    public void trade(Market market, Stats stats) {
        int profit = market.calculateProfit(stats.getTradingEfficiency());
        credits += profit;
    }

    public void research(Stats stats) {
        techPoints += 5 * stats.getResearchEfficiency();
        energy -= 10;
    }

    public int getCredits() { return credits; }
    public void spendCredits(int amount) { credits -= amount; }
}

class Economy {
    private Market market;

    public Economy() {
        this.market = new Market();
    }
}

class Market {
    private Map<String, Integer> prices;

    public Market() {
        this.prices = new HashMap<>();
        initializePrices();
    }

    private void initializePrices() {
        prices.put("energy", 10);
        prices.put("minerals", 20);
        prices.put("techPoints", 50);
    }

    public int calculateProfit(int tradingEfficiency) {
        return 100 + (20 * tradingEfficiency);
    }
}

// Research system
class ResearchSystem {
    private Map<String, Technology> techTree;

    public ResearchSystem() {
        this.techTree = new HashMap<>();
        initializeTechTree();
    }

    private void initializeTechTree() {
        techTree.put("weapons", new Technology("Basic Weapons"));
        techTree.put("shields", new Technology("Basic Shields"));
        techTree.put("propulsion", new Technology("Basic Propulsion"));
    }
}

class Technology {
    private String name;
    private int level;
    private int researchCost;

    public Technology(String name) {
        this.name = name;
        this.level = 1;
        this.researchCost = 100;
    }
}

// Combat System
class CombatSystem {
    private List<CombatLog> combatLogs;
    private Map<String, CombatInstance> activeBattles;
    private static final int TURN_TIME_LIMIT = 30; // seconds

    public CombatSystem() {
        this.combatLogs = new ArrayList<>();
        this.activeBattles = new HashMap<>();
    }

    public CombatInstance initiatePvPBattle(Player attacker, Player defender) {
        String battleId = UUID.randomUUID().toString();
        CombatInstance battle = new CombatInstance(battleId, attacker, defender, true);
        activeBattles.put(battleId, battle);
        return battle;
    }

    public CombatInstance initiatePvEBattle(Player player, List<Ship> enemyShips) {
        Fleet enemyFleet = new Fleet();
        enemyShips.forEach(enemyFleet::addShip);
        String battleId = UUID.randomUUID().toString();
        Player enemyPlayer = new Player();
        enemyPlayer.getFleet().addShip(new Ship("Enemy Ship"));
        CombatInstance battle = new CombatInstance(battleId, player, enemyPlayer, false);
        activeBattles.put(battleId, battle);
        return battle;
    }

    public CombatResult initiateCombat(Fleet attacker, Fleet defender) {
        CombatLog log = new CombatLog();
        boolean victory = false;

        List<Ship> attackerShips = new ArrayList<>(attacker.getShips());
        List<Ship> defenderShips = new ArrayList<>(defender.getShips());

        // Sort ships by position
        Collections.sort(attackerShips, (a, b) -> a.getPosition().compareTo(b.getPosition()));
        Collections.sort(defenderShips, (a, b) -> a.getPosition().compareTo(b.getPosition()));

        int round = 1;
        while (!attackerShips.isEmpty() && !defenderShips.isEmpty()) {
            log.addRound("Round " + round++);

            // Attacker's turn
            for (Ship attackingShip : attackerShips) {
                Ship target = selectTarget(defenderShips);
                int damage = calculateDamage(attackingShip);

                // Apply shield damage first
                if (target.getShields() > 0) {
                    int shieldDamage = Math.min(damage, target.getShields());
                    target.setShields(target.getShields() - shieldDamage);
                    damage -= shieldDamage;
                    log.addRound(attackingShip.getName() + " hits " + target.getName() + "'s shields for " + shieldDamage);
                }

                // Remaining damage goes to hull
                if (damage > 0) {
                    target.setHealth(target.getHealth() - damage);
                    log.addRound(attackingShip.getName() + " hits " + target.getName() + "'s hull for " + damage);
                }

                // Remove destroyed ships
                if (target.getHealth() <= 0) {
                    defenderShips.remove(target);
                    log.addRound(target.getName() + " has been destroyed!");
                }
            }

            // Defender's turn if still alive
            if (!defenderShips.isEmpty()) {
                for (Ship defendingShip : defenderShips) {
                    Ship target = selectTarget(attackerShips);
                    int damage = calculateDamage(defendingShip);

                    if (target.getShields() > 0) {
                        int shieldDamage = Math.min(damage, target.getShields());
                        target.setShields(target.getShields() - shieldDamage);
                        damage -= shieldDamage;
                        log.addRound(defendingShip.getName() + " hits " + target.getName() + "'s shields for " + shieldDamage);
                    }

                    if (damage > 0) {
                        target.setHealth(target.getHealth() - damage);
                        log.addRound(defendingShip.getName() + " hits " + target.getName() + "'s hull for " + damage);
                    }

                    if (target.getHealth() <= 0) {
                        attackerShips.remove(target);
                        log.addRound(target.getName() + " has been destroyed!");
                    }
                }
            }
        }

        victory = !attackerShips.isEmpty();
        return new CombatResult(victory, calculateRewards());
    }

    private int calculateFleetHealth(Fleet fleet) {
        return fleet.getShips().stream()
            .mapToInt(ship -> ship.getHealth() + ship.getShields())
            .sum();
    }

    private int calculateDamage(Ship ship) {
        int baseDamage = ship.getAttack();
        // Add critical hit chance
        if (Math.random() < 0.15) { // 15% crit chance
            baseDamage *= 1.5;
        }
        // Add random variance (±10%)
        double variance = 0.9 + (Math.random() * 0.2);
        return (int)(baseDamage * variance);
    }

    private Rewards calculateRewards() {
        return new Rewards(100, 50, 25); // credits, minerals, tech points
    }

    private Ship selectTarget(List<Ship> ships) {
        // Prioritize ships with low health or no shields
        return ships.stream()
            .min((a, b) -> {
                int aTotal = a.getShields() + a.getHealth();
                int bTotal = b.getShields() + b.getHealth();
                return Integer.compare(aTotal, bTotal);
            })
            .orElse(ships.get(0));
    }
}

class CombatLog {
    private List<String> rounds;
    private Date timestamp;

    public CombatLog() {
        this.rounds = new ArrayList<>();
        this.timestamp = new Date();
    }

    public void addRound(String roundDescription) {
        rounds.add(roundDescription);
    }
}

class CombatResult {
    public boolean victory;
    public Rewards rewards;

    public CombatResult(boolean victory, Rewards rewards) {
        this.victory = victory;
        this.rewards = rewards;
    }
}

class Rewards {
    private int credits;
    private int minerals;
    private int techPoints;

    public Rewards(int credits, int minerals, int techPoints) {
        this.credits = credits;
        this.minerals = minerals;
        this.techPoints = techPoints;
    }
}

// Mission System
class MissionSystem {
    private List<Mission> availableMissions;
    private List<Mission> completedMissions;

    public MissionSystem() {
        this.availableMissions = new ArrayList<>();
        this.completedMissions = new ArrayList<>();
        generateMissions();
    }

    private void generateMissions() {
        availableMissions.add(new Mission("Exploration", "Explore new sector", 100));
        availableMissions.add(new Mission("Combat", "Defeat enemy fleet", 200));
        availableMissions.add(new Mission("Resource", "Collect minerals", 150));
    }
}

class Mission {
    private String type;
    private String description;
    private int rewardCredits;
    private boolean completed;

    public Mission(String type, String description, int rewardCredits) {
        this.type = type;
        this.description = description;
        this.rewardCredits = rewardCredits;
        this.completed = false;
    }
}

// Alliance System
class AllianceSystem {
    private Map<String, Alliance> alliances;

    public AllianceSystem() {
        this.alliances = new HashMap<>();
    }

    public Alliance createAlliance(String name, Player founder) {
        Alliance alliance = new Alliance(name, founder);
        alliances.put(name, alliance);
        return alliance;
    }
}

class Alliance {
    private String name;
    private Player leader;
    private List<Player> members;
    private Resources sharedResources;

    public Alliance(String name, Player leader) {
        this.name = name;
        this.leader = leader;
        this.members = new ArrayList<>();
        this.sharedResources = new Resources();
        members.add(leader);
    }
}

// Enhanced Game class
// Dungeon System
class DungeonWorld {
    public String name;
    public String theme;
    public int minLevel;
    public List<Dungeon> dungeons;
    public Resources entryFee;

    public DungeonWorld(String name, String theme, int minLevel) {
        this.name = name;
        this.theme = theme;
        this.minLevel = minLevel;
        this.dungeons = new ArrayList<>();
        this.entryFee = new Resources();
        initializeDungeons();
    }

    private void initializeDungeons() {
        switch (theme) {
            case "SPACE":
                dungeons.add(new Dungeon("Abandoned Space Station", minLevel, 3));
                dungeons.add(new Dungeon("Alien Mothership", minLevel + 1, 4));
                dungeons.add(new Dungeon("Black Hole Laboratory", minLevel + 2, 5));
                break;
            case "CYBER":
                dungeons.add(new Dungeon("Digital Matrix", minLevel, 3));
                dungeons.add(new Dungeon("Quantum Server", minLevel + 1, 4));
                dungeons.add(new Dungeon("AI Core Complex", minLevel + 2, 5));
                break;

            case "ANCIENT":
                dungeons.add(new Dungeon("Lost Temple", minLevel, 3));
                dungeons.add(new Dungeon("Ancient Ruins", minLevel + 1, 4));
                dungeons.add(new Dungeon("Forgotten City", minLevel + 2, 5));
                break;
            case "QUANTUM":
                dungeons.add(new Dungeon("Probability Maze", minLevel, 4));
                dungeons.add(new Dungeon("Superposition Chamber", minLevel + 1, 5));
                dungeons.add(new Dungeon("Entanglement Nexus", minLevel + 2, 6));
                break;
            case "TIME":
                dungeons.add(new Dungeon("Past Echoes", minLevel, 4));
                dungeons.add(new Dungeon("Future Paradox", minLevel + 1, 5));
                dungeons.add(new Dungeon("Temporal Void", minLevel + 2, 6));
                break;
            case "ELEMENTAL":
                dungeons.add(new Dungeon("Fire Forge", minLevel, 5));
                dungeons.add(new Dungeon("Ice Citadel", minLevel + 1, 6));
                dungeons.add(new Dungeon("Storm Spire", minLevel + 2, 7));
                break;
        }
    }
}

class DungeonSystem {
    private List<DungeonWorld> dungeonWorlds;
    private Map<Player, List<DungeonRun>> dungeonHistory;

    public DungeonSystem() {
        this.dungeonWorlds = new ArrayList<>();
        this.dungeonHistory = new HashMap<>();
        initializeDungeonWorlds();
    }

    private void initializeDungeonWorlds() {
        dungeonWorlds.add(new DungeonWorld("Deep Space", "SPACE", 1));
        dungeonWorlds.add(new DungeonWorld("Cyber Realm", "CYBER", 3));
        dungeonWorlds.add(new DungeonWorld("Ancient Civilization", "ANCIENT", 5));
        dungeonWorlds.add(new DungeonWorld("Quantum Dimension", "QUANTUM", 7));
        dungeonWorlds.add(new DungeonWorld("Temporal Nexus", "TIME", 10));
        dungeonWorlds.add(new DungeonWorld("Elemental Plane", "ELEMENTAL", 15));
    }

    public DungeonWorld getWorldByName(String name) {
        return dungeonWorlds.stream()
            .filter(world -> world.name.equals(name))
            .findFirst()
            .orElse(null);
    }

    public DungeonRun startDungeon(Player player, Dungeon dungeon) {
        if (player.getLevel() < dungeon.getRequiredLevel()) {
            throw new IllegalStateException("Player level too low for this dungeon");
        }
        return new DungeonRun(dungeon, player);
    }
}

class Dungeon {
    public String name;
    private int requiredLevel;
    public int numberOfRooms;
    public List<DungeonRoom> rooms;
    private Rewards completionRewards;

    public Dungeon(String name, int requiredLevel, int numberOfRooms) {
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.numberOfRooms = numberOfRooms;
        this.rooms = generateRooms();
        this.completionRewards = new Rewards(500, 250, 100);
    }

    private List<DungeonRoom> generateRooms() {
        List<DungeonRoom> generatedRooms = new ArrayList<>();
        for (int i = 0; i < numberOfRooms; i++) {
            generatedRooms.add(new DungeonRoom("Room " + (i + 1), 
                new Fleet(), new Resources()));
        }
        return generatedRooms;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }
}

class DungeonRoom {
    private String name;
    public Fleet enemies;
    private Resources loot;
    public boolean cleared;

    public DungeonRoom(String name, Fleet enemies, Resources loot) {
        this.name = name;
        this.enemies = enemies;
        this.loot = loot;
        this.cleared = false;
    }
}

class DungeonRun {
    private Dungeon dungeon;
    private Player player;
    private int currentRoom;
    private boolean completed;
    private Date startTime;

    public DungeonRun(Dungeon dungeon, Player player) {
        this.dungeon = dungeon;
        this.player = player;
        this.currentRoom = 0;
        this.completed = false;
        this.startTime = new Date();
    }

    public boolean progressRoom(CombatSystem combat) {
        if (currentRoom >= dungeon.numberOfRooms) {
            completed = true;
            return false;
        }

        DungeonRoom room = dungeon.rooms.get(currentRoom);
        CombatResult result = combat.initiateCombat(player.getFleet(), room.enemies);

        if (result.victory) {
            room.cleared = true;
            currentRoom++;
            return true;
        }
        return false;
    }
}

class Game {
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

    public Game() {
        this.messageSystem = new MessageSystem();
        this.player = new Player();
        this.universe = new Universe();
        this.celestialBodies = initializeCelestialBodies();
        this.economy = new Economy();
        this.research = new ResearchSystem();
        this.combat = new CombatSystem();
        this.missions = new MissionSystem();
        this.alliances = new AllianceSystem();
        this.dungeons = new DungeonSystem();
        this.goddessSystem = new GoddessSystem();
    }

    public void start() {
        System.out.println("Enhanced game initialized with Combat, Missions, and Alliance systems!");
        try {
            serverSocket = new ServerSocket(12345); // Choose a port
            System.out.println("Server started on port 12345");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testGameSystems() {
        // Test player creation and leveling
        player.gainExperience(1500);
        System.out.println("Player Level: " + player.getLevel());

        // Test fleet system
        player.getFleet().addShip(new Ship("Battleship"));
        System.out.println("Fleet Size: " + player.getFleet().getShips().size());

        // Test combat system
        Fleet enemyFleet = new Fleet();
        enemyFleet.addShip(new Ship("Enemy Ship"));
        CombatResult result = combat.initiateCombat(player.getFleet(), enemyFleet);
        System.out.println("Combat Victory: " + result.victory);
    }
    private Map<String, CelestialBody> initializeCelestialBodies() {
        Map<String, CelestialBody> bodies = new HashMap<>();
        bodies.put("Sun", new CelestialBody("Sun", new Coordinates(0, 0), 100, "Star"));
        return bodies;
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    // Process client message
                    System.out.println("Client sent: " + message);
                    // Send response
                    writer.println("Server received: " + message);
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
    }
}

class Player {
    private String name;
    private int level;
    private int experience;
    private Fleet fleet;
    private Stats stats;
    private Resources resources;

    public Player() {
        this.name = "Player";
        this.level = 1;
        this.experience = 0;
        this.fleet = new Fleet();
        this.stats = new Stats();
        this.resources = new Resources();
    }

    public void gainExperience(int amount) {
        experience += amount;
        while (experience >= level * 1000) {
            level++;
            experience -= level * 1000;
            stats.increaseStats();
            System.out.println("Level Up! Current level: " + level);
        }
    }

    public int getLevel() { return level; }
    public Fleet getFleet() { return fleet; }
}

class Fleet {
    private List<Ship> ships;

    public Fleet() {
        this.ships = new ArrayList<>();
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public List<Ship> getShips() { return ships; }
}


// Stats class moved to Stats.java

class CombatInstance {
    private String id;
    private Player player1;
    private Player player2;
    private boolean isPvP;
    private CombatLog log;

    public CombatInstance(String id, Player player1, Player player2, boolean isPvP) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.isPvP = isPvP;
        this.log = new CombatLog();
    }
}

class MessageSystem {
    private Queue<String> messages;

    public MessageSystem() {
        this.messages = new LinkedList<>();
    }

    public void addMessage(String message) {
        messages.offer(message);
    }

    public String getNextMessage() {
        return messages.poll();
    }

    public boolean hasMessages() {
        return !messages.isEmpty();
    }
}

class CelestialBody {
    private String name;
    private Coordinates coordinates;
    private double size;
    private String type;


    public CelestialBody(String name, Coordinates coordinates, double size, String type) {
        this.name = name;
        this.coordinates = coordinates;
        this.size = size;
        this.type = type;
    }

}


class GUI extends JFrame {
    private Game game;

    public GUI(Game game) {
        this.game = game;
        setTitle("Enhanced Space Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}


class UniverseGenerator {
    private long seed;
    private Random random;

    public UniverseGenerator(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

    public List<Galaxy> generateGalaxies() {
        List<Galaxy> galaxies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            double x = random.nextDouble() * 1000;
            double y = random.nextDouble() * 1000;
            galaxies.add(new Galaxy("Galaxy " + (i + 1), "Frontier", new Coordinates(x, y)));
        }
        return galaxies;
    }
}

class Goddess {
    private String name;
    private String domain;
    private int favorLevel;
    private Map<String, Integer> blessings;

    public Goddess(String name, String domain) {
        this.name = name;
        this.domain = domain;
        this.favorLevel = 0;
        this.blessings = new HashMap<>();
        initializeBlessings();
    }

    private void initializeBlessings() {
        switch (domain) {
            case "WAR":
                blessings.put("Attack Boost", 50);
                blessings.put("Shield Enhancement", 30);
                break;
            case "WISDOM":
                blessings.put("Research Boost", 40);
                blessings.put("Experience Boost", 25);
                break;
            case "FORTUNE":
                blessings.put("Resource Boost", 35);
                blessings.put("Trading Luck", 45);
                break;
        }
    }

    public void increaseFavor(int amount) {
        this.favorLevel += amount;
    }

    public int getBlessingPower(String blessing) {
        return blessings.getOrDefault(blessing, 0) * (1 + favorLevel / 100);
    }
}

class GoddessSystem {
    private Map<String, Goddess> goddesses;

    public GoddessSystem() {
        this.goddesses = new HashMap<>();
        initializeGoddesses();
    }

    private void initializeGoddesses() {
        goddesses.put("Athena", new Goddess("Athena", "WISDOM"));
        goddesses.put("Bellona", new Goddess("Bellona", "WAR"));
        goddesses.put("Fortuna", new Goddess("Fortuna", "FORTUNE"));
    }
}

class Stats {
    private int miningEfficiency;
    private int tradingEfficiency;
    private int researchEfficiency;

    public Stats() {
        this.miningEfficiency = 1;
        this.tradingEfficiency = 1;
        this.researchEfficiency = 1;
    }

    public void increaseStats() {
        miningEfficiency++;
        tradingEfficiency++;
researchEfficiency++;
    }

    public int getMiningEfficiency() { return miningEfficiency; }
    public int getTradingEfficiency() { return tradingEfficiency; }
    public int getResearchEfficiency() { return researchEfficiency; }
}