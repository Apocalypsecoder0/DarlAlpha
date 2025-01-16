
package main.java;

import java.util.*;

public class DungeonFinder {
    private Map<String, List<DungeonGroup>> activeDungeonGroups;
    private Map<String, List<Player>> queuedPlayers;

    public DungeonFinder() {
        this.activeDungeonGroups = new HashMap<>();
        this.queuedPlayers = new HashMap<>();
    }

    public void queueForDungeon(Player player, String dungeonName) {
        queuedPlayers.computeIfAbsent(dungeonName, k -> new ArrayList<>()).add(player);
        tryFormGroup(dungeonName);
    }

    private void tryFormGroup(String dungeonName) {
        List<Player> queue = queuedPlayers.get(dungeonName);
        if (queue != null && queue.size() >= 4) {  // Form group when 4 players are available
            List<Player> groupMembers = new ArrayList<>(queue.subList(0, 4));
            DungeonGroup newGroup = new DungeonGroup(dungeonName, groupMembers);
            
            activeDungeonGroups.computeIfAbsent(dungeonName, k -> new ArrayList<>()).add(newGroup);
            queue.subList(0, 4).clear();
            
            notifyGroupMembers(newGroup);
        }
    }

    private void notifyGroupMembers(DungeonGroup group) {
        for (Player player : group.getMembers()) {
            // Notify each player they've been matched
            player.sendMessage("Dungeon group formed for: " + group.getDungeonName());
        }
    }

    public List<DungeonGroup> getActiveGroups(String dungeonName) {
        return activeDungeonGroups.getOrDefault(dungeonName, new ArrayList<>());
    }

    public int getQueuePosition(Player player, String dungeonName) {
        List<Player> queue = queuedPlayers.get(dungeonName);
        if (queue != null) {
            return queue.indexOf(player) + 1;
        }
        return -1;
    }
}
