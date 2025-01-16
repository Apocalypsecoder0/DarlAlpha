
import java.util.*;

public class AscensionSystem {
    private int ascensionLevel;
    private Map<String, Double> ascensionBonuses;
    private List<AscensionPower> availablePowers;
    private Set<AscensionPower> unlockedPowers;
    
    public AscensionSystem() {
        this.ascensionLevel = 0;
        this.ascensionBonuses = new HashMap<>();
        this.availablePowers = initializePowers();
        this.unlockedPowers = new HashSet<>();
        initializeBonuses();
    }
    
    private List<AscensionPower> initializePowers() {
        List<AscensionPower> powers = new ArrayList<>();
        powers.add(new AscensionPower("Time Dilation", "Slow down time in combat", 1));
        powers.add(new AscensionPower("Divine Shield", "Immune to damage briefly", 2));
        powers.add(new AscensionPower("Cosmic Awareness", "See hidden enemies and treasures", 3));
        powers.add(new AscensionPower("Reality Bend", "Chance to avoid death", 5));
        return powers;
    }
    
    private void initializeBonuses() {
        ascensionBonuses.put("DAMAGE", 0.5);
        ascensionBonuses.put("HEALTH", 0.5);
        ascensionBonuses.put("SHIELD", 0.4);
        ascensionBonuses.put("RESOURCE_GAIN", 0.6);
    }
    
    public boolean canAscend(Player player) {
        return player.getPrestigeLevel() >= 10;
    }
    
    public void performAscension(Player player) {
        if (!canAscend(player)) {
            throw new IllegalStateException("Player does not meet ascension requirements");
        }
        
        ascensionLevel++;
        resetPrestige(player);
        applyAscensionBonuses(player);
        unlockNewPowers();
    }
    
    private void resetPrestige(Player player) {
        player.resetPrestigeLevel();
        // Keep some prestige bonuses as base stats
        player.getStats().addBaseStats(player.getPrestigeStats());
    }
    
    private void applyAscensionBonuses(Player player) {
        for (Map.Entry<String, Double> bonus : ascensionBonuses.entrySet()) {
            double multiplier = bonus.getValue() * ascensionLevel;
            player.getStats().addAscensionMultiplier(bonus.getKey(), multiplier);
        }
    }
    
    private void unlockNewPowers() {
        for (AscensionPower power : availablePowers) {
            if (power.requiredLevel <= ascensionLevel && !unlockedPowers.contains(power)) {
                unlockedPowers.add(power);
            }
        }
    }
    
    public Set<AscensionPower> getUnlockedPowers() {
        return unlockedPowers;
    }
    
    public int getAscensionLevel() {
        return ascensionLevel;
    }
}

class AscensionPower {
    private String name;
    private String description;
    public final int requiredLevel;
    
    public AscensionPower(String name, String description, int requiredLevel) {
        this.name = name;
        this.description = description;
        this.requiredLevel = requiredLevel;
    }
    
    public void activate(Player player) {
        // Implement power-specific effects
        switch (name) {
            case "Time Dilation":
                player.applyCombatEffect("TIME_SLOW", 10);
                break;
            case "Divine Shield":
                player.applyInvulnerability(5);
                break;
            case "Cosmic Awareness":
                player.revealHiddenEntities(30);
                break;
            case "Reality Bend":
                player.setDeathAvoidance(0.25);
                break;
        }
    }
}
