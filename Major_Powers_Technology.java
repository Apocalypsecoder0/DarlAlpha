
public class Major_Powers_Technology {
    private String name;
    private String type;
    private String description;
    private int researchCost;

    public Major_Powers_Technology(String name, String type, String description, int researchCost) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.researchCost = researchCost;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public int getResearchCost() { return researchCost; }
}
