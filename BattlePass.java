
import java.util.*;

class BattlePass {
    private int currentLevel;
    private int maxLevel;
    private Map<Integer, Reward> rewards;
    private boolean isPremium;
    private Date seasonEndDate;

    public BattlePass() {
        this.currentLevel = 1;
        this.maxLevel = 100;
        this.rewards = new HashMap<>();
        this.isPremium = false;
        this.seasonEndDate = calculateSeasonEnd();
        initializeRewards();
    }

    private Date calculateSeasonEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 3); // 3-month season
        return calendar.getTime();
    }

    private void initializeRewards() {
        // Free tier rewards
        for (int i = 1; i <= maxLevel; i++) {
            if (i % 5 == 0) { // Every 5 levels
                rewards.put(i, new Reward("credits", 1000 * (i/5)));
            }
        }

        // Premium tier rewards
        if (isPremium) {
            for (int i = 1; i <= maxLevel; i++) {
                if (i % 10 == 0) { // Every 10 levels
                    rewards.put(i, new Reward("unique_ship", "Premium Ship " + i));
                }
            }
        }
    }

    public void upgradeToPremiun() {
        this.isPremium = true;
        initializeRewards();
    }

    public boolean levelUp() {
        if (currentLevel < maxLevel) {
            currentLevel++;
            return true;
        }
        return false;
    }

    public Reward getReward(int level) {
        return rewards.get(level);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
