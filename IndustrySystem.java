
import java.util.*;

public class IndustrySystem {
    private Map<String, Blueprint> blueprints;
    private Map<String, IndustrialFacility> facilities;
    private List<ProductionJob> activeJobs;
    
    public IndustrySystem() {
        this.blueprints = new HashMap<>();
        this.facilities = new HashMap<>();
        this.activeJobs = new ArrayList<>();
    }
    
    public void startProduction(String blueprintId, String facilityId, Player player) {
        Blueprint bp = blueprints.get(blueprintId);
        IndustrialFacility facility = facilities.get(facilityId);
        
        if (bp != null && facility != null && facility.canProduce(bp) && 
            player.hasResources(bp.getRequirements())) {
            ProductionJob job = new ProductionJob(bp, player, facility);
            activeJobs.add(job);
            facility.startJob(job);
        }
    }
    
    public void researchBlueprint(Blueprint blueprint, int researchPoints) {
        if (researchPoints >= blueprint.getResearchCost()) {
            blueprint.improve();
            blueprints.put(blueprint.getId(), blueprint);
        }
    }
    
    public void extractResources(String facilityId, String resourceType, int amount) {
        IndustrialFacility facility = facilities.get(facilityId);
        if (facility != null && facility.canExtract(resourceType)) {
            facility.startExtraction(resourceType, amount);
        }
    }
}

class Blueprint {
    private String id;
    private String productId;
    private Map<String, Integer> requirements;
    private int productionTime;
    private int techLevel;
    private int researchCost;
    private double efficiencyModifier;
    private List<String> improvements;
    
    public Blueprint(String id, String productId, Map<String, Integer> requirements, int productionTime) {
        this.id = id;
        this.productId = productId;
        this.requirements = requirements;
        this.productionTime = productionTime;
        this.techLevel = 1;
    }
    
    public void improve(int level) {
        techLevel = level;
        productionTime = (int)(productionTime * (0.9 * level));
    }
    
    public String getId() { return id; }
    public Map<String, Integer> getRequirements() { return requirements; }
}

class IndustrialFacility {
    private String id;
    private List<String> supportedBlueprints;
    private int efficiency;
    private ProductionJob currentJob;
    private Map<String, Integer> extractionRates;
    private Map<String, Integer> resourceStorage;
    private int maxStorage;
    
    public IndustrialFacility(String id, int efficiency, int maxStorage) {
        this.id = id;
        this.efficiency = efficiency;
        this.maxStorage = maxStorage;
        this.supportedBlueprints = new ArrayList<>();
        this.extractionRates = new HashMap<>();
        this.resourceStorage = new HashMap<>();
    }
    
    public boolean canProduce(Blueprint blueprint) {
        return supportedBlueprints.contains(blueprint.getId()) && currentJob == null;
    }
    
    public boolean canExtract(String resourceType) {
        return extractionRates.containsKey(resourceType) && 
               resourceStorage.getOrDefault(resourceType, 0) < maxStorage;
    }
    
    public void startJob(ProductionJob job) {
        currentJob = job;
    }
    
    public void startExtraction(String resourceType, int amount) {
        if (canExtract(resourceType)) {
            int currentAmount = resourceStorage.getOrDefault(resourceType, 0);
            resourceStorage.put(resourceType, Math.min(currentAmount + amount, maxStorage));
        }
    }
    
    public void upgradeEfficiency(int amount) {
        efficiency += amount;
        for (String resource : extractionRates.keySet()) {
            extractionRates.put(resource, extractionRates.get(resource) + amount);
        }
    }
}
