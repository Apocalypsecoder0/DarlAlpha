
package main.java;

import java.util.*;

public class DungeonGroup {
    private String dungeonName;
    private List<Player> members;
    private boolean isActive;
    private Date formationTime;

    public DungeonGroup(String dungeonName, List<Player> members) {
        this.dungeonName = dungeonName;
        this.members = members;
        this.isActive = true;
        this.formationTime = new Date();
    }

    public String getDungeonName() { return dungeonName; }
    public List<Player> getMembers() { return members; }
    public boolean isActive() { return isActive; }
    public Date getFormationTime() { return formationTime; }

    public void disbandGroup() {
        isActive = false;
        members.clear();
    }
}
