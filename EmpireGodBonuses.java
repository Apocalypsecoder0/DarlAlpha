
import java.util.Map;
import java.util.HashMap;

public class EmpireGodBonuses {
    private Goddess patronGoddess;
    private double faithPoints;
    private Map<String, Double> currentBonuses;

    public EmpireGodBonuses(Goddess patronGoddess) {
        this.patronGoddess = patronGoddess;
        this.faithPoints = 0;
        this.currentBonuses = new HashMap<>();
        initializeBonuses();
    }

    private void initializeBonuses() {
        switch (patronGoddess.getDomain()) {
            case "WAR":
                currentBonuses.put("ATTACK", 1.1);
                currentBonuses.put("DEFENSE", 1.05);
                break;
            case "WISDOM":
                currentBonuses.put("RESEARCH", 1.15);
                currentBonuses.put("EXPERIENCE", 1.1);
                break;
            case "FORTUNE":
                currentBonuses.put("RESOURCES", 1.1);
                currentBonuses.put("TRADE", 1.15);
                break;
        }
    }

    public double getBonusMultiplier(String bonusType) {
        return currentBonuses.getOrDefault(bonusType, 1.0) * (1 + faithPoints/100);
    }

    public void increaseFaith(double points) {
        this.faithPoints += points;
        if (this.faithPoints > 100) {
            this.faithPoints = 100;
        }
    }

    public Goddess getPatronGoddess() {
        return patronGoddess;
    }

    public double getFaithPoints() {
        return faithPoints;
    }
}
