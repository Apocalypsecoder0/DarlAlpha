
public class Equipment {
    private String name;
    private String type;
    private Map<String, Integer> stats;
    private int level;
    private String rarity;

    public Equipment(String name, String type, Map<String, Integer> stats, int level, String rarity) {
        this.name = name;
        this.type = type;
        this.stats = stats;
        this.level = level;
        this.rarity = rarity;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public Map<String, Integer> getStats() { return stats; }
    public int getLevel() { return level; }
    public String getRarity() { return rarity; }
}
