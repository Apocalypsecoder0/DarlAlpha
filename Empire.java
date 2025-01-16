
import java.util.List;
import java.util.ArrayList;

public class Empire {
    private String name;
    private Player ruler;
    private List<Territory> territories;
    private Resources resources;
    private List<Player> citizens;
    private Technology empireTech;
    private EmpireGodBonuses godBonuses;
    private RaceEvolutionSystem raceEvolution;
    
    public Empire(String name, Player ruler, Goddess patronGoddess) {
        this.raceEvolution = new RaceEvolutionSystem();
        this.godBonuses = new EmpireGodBonuses(patronGoddess);
        this.name = name;
        this.ruler = ruler;
        this.territories = new ArrayList<>();
        this.resources = new Resources();
        this.citizens = new ArrayList<>();
        this.empireTech = new Technology("Empire Base Tech");
        this.citizens.add(ruler);
    }
    
    public void addTerritory(Territory territory) {
        territories.add(territory);
        updateResourcesFromTerritory(territory);
    }
    
    private void updateResourcesFromTerritory(Territory territory) {
        // Add territory resources to empire resources
        this.resources.add(territory.getResources());
    }
    
    public void addCitizen(Player player) {
        if (!citizens.contains(player)) {
            citizens.add(player);
        }
    }
    
    // Getters
    public String getName() { return name; }
    public Player getRuler() { return ruler; }
    public List<Territory> getTerritories() { return territories; }
    public Resources getResources() { return resources; }
    public List<Player> getCitizens() { return citizens; }
    public EmpireGodBonuses getGodBonuses() { return godBonuses; }
    
    public double getGodBonus(String bonusType) {
        return godBonuses.getBonusMultiplier(bonusType);
    }
}
