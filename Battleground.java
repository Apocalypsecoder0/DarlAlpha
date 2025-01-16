
import java.util.*;

public class Battleground {
    private Map<String, BattleTeam> teams;
    private boolean isActive;
    private int roundTime;
    private int maxPlayers = 3;
    
    public Battleground() {
        this.teams = new HashMap<>();
        teams.put("RED", new BattleTeam("RED"));
        teams.put("BLUE", new BattleTeam("BLUE"));
        this.roundTime = 900; // 15 minutes
    }
    
    public boolean addPlayer(Player player, String teamColor) {
        BattleTeam team = teams.get(teamColor);
        if (team != null && team.getSize() < maxPlayers) {
            return team.addPlayer(player);
        }
        return false;
    }
    
    public void startBattle() {
        if (areTeamsReady()) {
            isActive = true;
            // Initialize combat instance for both teams
            List<Player> teamRed = teams.get("RED").getPlayers();
            List<Player> teamBlue = teams.get("BLUE").getPlayers();
            // Start the battle logic
        }
    }
    
    private boolean areTeamsReady() {
        return teams.values().stream().allMatch(team -> team.getSize() == maxPlayers);
    }
}

class BattleTeam {
    private String color;
    private List<Player> players;
    private int score;
    
    public BattleTeam(String color) {
        this.color = color;
        this.players = new ArrayList<>();
        this.score = 0;
    }
    
    public boolean addPlayer(Player player) {
        if (players.size() < 3) {
            players.add(player);
            return true;
        }
        return false;
    }
    
    public int getSize() {
        return players.size();
    }
    
    public List<Player> getPlayers() {
        return players;
    }
}
