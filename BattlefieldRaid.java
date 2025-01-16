
import java.util.*;

public class BattlefieldRaid {
    private List<Player> raiders;
    private RaidBoss raidBoss;
    private boolean isActive;
    private int maxRaiders = 6;
    private Map<String, Integer> damageDealt;
    
    public BattlefieldRaid() {
        this.raiders = new ArrayList<>();
        this.damageDealt = new HashMap<>();
    }
    
    public boolean addRaider(Player player) {
        if (raiders.size() < maxRaiders) {
            raiders.add(player);
            damageDealt.put(player.getName(), 0);
            return true;
        }
        return false;
    }
    
    public void startRaid() {
        if (raiders.size() == maxRaiders) {
            isActive = true;
            raidBoss = new RaidBoss("Battlefield Boss", 100);
            // Initialize raid combat instance
        }
    }
    
    public void recordDamage(Player player, int damage) {
        damageDealt.merge(player.getName(), damage, Integer::sum);
    }
    
    public Map<String, Integer> getDamageLeaderboard() {
        return new LinkedHashMap<>(damageDealt);
    }
}
