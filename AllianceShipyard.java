
import java.util.*;

public class AllianceShipyard {
    private List<AllianceShipProject> projects;
    private int maxConcurrentProjects;
    private Map<String, Integer> memberContributions;

    public AllianceShipyard() {
        this.projects = new ArrayList<>();
        this.maxConcurrentProjects = 3;
        this.memberContributions = new HashMap<>();
    }

    public boolean startProject(String shipType, Map<String, Integer> resourceRequirements, int buildTime) {
        if (projects.size() >= maxConcurrentProjects) {
            return false;
        }
        
        AllianceShipProject project = new AllianceShipProject(shipType, resourceRequirements, buildTime);
        projects.add(project);
        return true;
    }

    public boolean contributeResources(Player player, String projectId, Map<String, Integer> resources) {
        AllianceShipProject project = findProject(projectId);
        if (project == null || !project.canContribute()) {
            return false;
        }

        if (project.addContribution(player, resources)) {
            memberContributions.merge(player.getAccount().getUsername(), 1, Integer::sum);
            return true;
        }
        return false;
    }

    public List<Ship> checkCompletedProjects() {
        List<Ship> completedShips = new ArrayList<>();
        Iterator<AllianceShipProject> iterator = projects.iterator();
        
        while (iterator.hasNext()) {
            AllianceShipProject project = iterator.next();
            if (project.isComplete()) {
                completedShips.add(new Ship(project.getShipType()));
                iterator.remove();
            }
        }
        return completedShips;
    }

    private AllianceShipProject findProject(String projectId) {
        return projects.stream()
            .filter(p -> p.getId().equals(projectId))
            .findFirst()
            .orElse(null);
    }
}

class AllianceShipProject {
    private String id;
    private String shipType;
    private Map<String, Integer> requiredResources;
    private Map<String, Integer> currentResources;
    private long startTime;
    private int buildTime;
    private Set<String> contributors;

    public AllianceShipProject(String shipType, Map<String, Integer> resourceRequirements, int buildTime) {
        this.id = UUID.randomUUID().toString();
        this.shipType = shipType;
        this.requiredResources = new HashMap<>(resourceRequirements);
        this.currentResources = new HashMap<>();
        this.startTime = System.currentTimeMillis();
        this.buildTime = buildTime;
        this.contributors = new HashSet<>();
    }

    public boolean addContribution(Player player, Map<String, Integer> resources) {
        String username = player.getAccount().getUsername();
        if (!canContribute()) return false;

        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            currentResources.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
        contributors.add(username);
        return true;
    }

    public boolean canContribute() {
        return !isComplete() && !hasAllResources();
    }

    public boolean isComplete() {
        return hasAllResources() && (System.currentTimeMillis() - startTime >= buildTime);
    }

    private boolean hasAllResources() {
        return requiredResources.entrySet().stream()
            .allMatch(entry -> 
                currentResources.getOrDefault(entry.getKey(), 0) >= entry.getValue());
    }

    public String getId() { return id; }
    public String getShipType() { return shipType; }
}
