
import java.util.*;

public class ExpansionSystem {
    private Map<String, Territory> territories;
    private Map<Player, List<Territory>> playerTerritories;
    private Map<String, List<Resource>> territoryResources;

    public ExpansionSystem() {
        this.territories = new HashMap<>();
        this.playerTerritories = new HashMap<>();
        this.territoryResources = new HashMap<>();
        initializeTerritories();
    }

    private void initializeTerritories() {
        addTerritory("Alpha Sector", 1, Arrays.asList(new Resource("Minerals", 100), new Resource("Energy", 50)));
        addTerritory("Beta Quadrant", 2, Arrays.asList(new Resource("Dark Matter", 75), new Resource("Plasma", 60)));
        addTerritory("Gamma Zone", 3, Arrays.asList(new Resource("Antimatter", 150), new Resource("Deuterium", 80)));
    }

    private void addTerritory(String name, int controlLevel, List<Resource> resources) {
        Territory territory = new Territory(name, controlLevel);
        territories.put(name, territory);
        territoryResources.put(name, resources);
    }

    public boolean claimTerritory(Player player, String territoryName) {
        Territory territory = territories.get(territoryName);
        if (territory != null && !territory.isClaimed() && player.getLevel() >= territory.getRequiredLevel()) {
            territory.setClaimed(true);
            territory.setOwner(player);
            playerTerritories.computeIfAbsent(player, k -> new ArrayList<>()).add(territory);
            return true;
        }
        return false;
    }

    public List<Territory> getPlayerTerritories(Player player) {
        return playerTerritories.getOrDefault(player, new ArrayList<>());
    }

    public List<Resource> getTerritoryResources(String territoryName) {
        return territoryResources.getOrDefault(territoryName, new ArrayList<>());
    }
}

class Territory {
    private String name;
    private int requiredLevel;
    private boolean claimed;
    private Player owner;

    public Territory(String name, int requiredLevel) {
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.claimed = false;
    }

    public String getName() { return name; }
    public int getRequiredLevel() { return requiredLevel; }
    public boolean isClaimed() { return claimed; }
    public void setClaimed(boolean claimed) { this.claimed = claimed; }
    public Player getOwner() { return owner; }
    public void setOwner(Player owner) { this.owner = owner; }
}
