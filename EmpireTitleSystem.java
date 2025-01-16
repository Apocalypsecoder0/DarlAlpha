
import java.util.*;

public class EmpireTitleSystem {
    private Map<String, Set<Title>> empireTitles;
    private List<Title> availableTitles;

    public EmpireTitleSystem() {
        this.empireTitles = new HashMap<>();
        this.availableTitles = initializeTitles();
    }

    private List<Title> initializeTitles() {
        List<Title> titles = new ArrayList<>();
        
        // Military Titles
        titles.add(new Title("Warlord", "Control 10 military outposts", TitleCategory.MILITARY));
        titles.add(new Title("Fleet Admiral", "Command 50 ships", TitleCategory.MILITARY));
        titles.add(new Title("Conqueror", "Win 100 battles", TitleCategory.MILITARY));
        
        // Economic Titles
        titles.add(new Title("Trade Baron", "Earn 1,000,000 credits", TitleCategory.ECONOMIC));
        titles.add(new Title("Resource Magnate", "Control 20 resource nodes", TitleCategory.ECONOMIC));
        titles.add(new Title("Economic Overlord", "Establish 10 trade routes", TitleCategory.ECONOMIC));
        
        // Diplomatic Titles
        titles.add(new Title("Ambassador", "Form 5 alliances", TitleCategory.DIPLOMATIC));
        titles.add(new Title("Peacekeeper", "Resolve 10 conflicts", TitleCategory.DIPLOMATIC));
        titles.add(new Title("Grand Diplomat", "Maintain perfect relations with 3 empires", TitleCategory.DIPLOMATIC));
        
        // Special Titles
        titles.add(new Title("Emperor", "Rule for 30 days", TitleCategory.SPECIAL));
        titles.add(new Title("Legendary", "Complete all achievements", TitleCategory.SPECIAL));
        titles.add(new Title("Immortal", "Reach max level", TitleCategory.SPECIAL));
        
        return titles;
    }

    public void checkAndAwardTitles(String empireId, Empire empire) {
        for (Title title : availableTitles) {
            if (!hasTitle(empireId, title) && meetsRequirements(empire, title)) {
                awardTitle(empireId, title);
            }
        }
    }

    private boolean meetsRequirements(Empire empire, Title title) {
        switch (title.getName()) {
            case "Warlord":
                return empire.getMilitaryOutposts() >= 10;
            case "Fleet Admiral":
                return empire.getFleetSize() >= 50;
            case "Trade Baron":
                return empire.getTotalCredits() >= 1000000;
            // Add more requirement checks
            default:
                return false;
        }
    }

    public void awardTitle(String empireId, Title title) {
        empireTitles.computeIfAbsent(empireId, k -> new HashSet<>()).add(title);
    }

    public boolean hasTitle(String empireId, Title title) {
        return empireTitles.getOrDefault(empireId, new HashSet<>()).contains(title);
    }

    public Set<Title> getEmpireTitles(String empireId) {
        return empireTitles.getOrDefault(empireId, new HashSet<>());
    }
}

class Title {
    private String name;
    private String requirement;
    private TitleCategory category;

    public Title(String name, String requirement, TitleCategory category) {
        this.name = name;
        this.requirement = requirement;
        this.category = category;
    }

    public String getName() { return name; }
    public String getRequirement() { return requirement; }
    public TitleCategory getCategory() { return category; }
}

enum TitleCategory {
    MILITARY,
    ECONOMIC,
    DIPLOMATIC,
    SPECIAL
}
