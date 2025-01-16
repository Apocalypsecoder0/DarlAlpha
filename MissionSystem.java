
public class MissionSystem {
    private Map<String, Mission> availableMissions;
    private List<Mission> completedMissions;
    private Random random;

    public MissionSystem() {
        this.availableMissions = new HashMap<>();
        this.completedMissions = new ArrayList<>();
        this.random = new Random();
        generateDailyMissions();
    }

    private void generateDailyMissions() {
        String[] missionTypes = {"Combat", "Exploration", "Mining", "Trading", "Bounty"};
        
        for (int i = 0; i < 5; i++) {
            String type = missionTypes[random.nextInt(missionTypes.length)];
            String id = UUID.randomUUID().toString();
            Mission mission = generateMission(type, id);
            availableMissions.put(id, mission);
        }
    }

    private Mission generateMission(String type, String id) {
        switch (type) {
            case "Combat":
                return new Mission(id, type, "Defeat enemy ships", 1000, 1);
            case "Exploration":
                return new Mission(id, type, "Explore new sectors", 800, 1);
            case "Mining":
                return new Mission(id, type, "Mine resources", 600, 1);
            case "Trading":
                return new Mission(id, type, "Complete trade routes", 700, 1);
            case "Bounty":
                return new Mission(id, type, "Hunt down pirates", 1200, 2);
            default:
                return new Mission(id, "Basic", "Complete basic tasks", 500, 1);
        }
    }

    public void updateMissionProgress(String missionId, String objective, int progress) {
        Mission mission = availableMissions.get(missionId);
        if (mission != null) {
            mission.updateProgress(objective, progress);
            if (mission.isCompleted()) {
                completedMissions.add(mission);
                availableMissions.remove(missionId);
            }
        }
    }

    public void refreshDailyMissions() {
        availableMissions.clear();
        generateDailyMissions();
    }

    public List<Mission> getAvailableMissions() {
        return new ArrayList<>(availableMissions.values());
    }

    public List<Mission> getCompletedMissions() {
        return completedMissions;
    }

    public Mission getMission(String id) {
        return availableMissions.get(id);
    }
}
