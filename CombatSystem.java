package main.java;

import java.util.*;

/**
 * Handles all combat-related functionality in the game.
 */
public class CombatSystem {
    private static final double CRIT_CHANCE = 0.15;
    private static final double DAMAGE_VARIANCE = 0.1;

    private List<CombatLog> combatLogs;
    private Map<String, CombatInstance> activeBattles;

    public CombatSystem() {
        this.combatLogs = new ArrayList<>();
        this.activeBattles = new HashMap<>();
    }

    /**
     * Initiates combat between two fleets and returns the result.
     */
    public CombatResult initiateCombat(Fleet attacker, Fleet defender) {
        CombatLog log = new CombatLog();

        List<Ship> attackerShips = new ArrayList<>(attacker.getShips());
        List<Ship> defenderShips = new ArrayList<>(defender.getShips());

        // Sort ships by position for tactical combat
        sortShipsByPosition(attackerShips);
        sortShipsByPosition(defenderShips);

        boolean victory = processCombatRounds(attackerShips, defenderShips, log);

        return new CombatResult(victory, calculateRewards());
    }

    private void sortShipsByPosition(List<Ship> ships) {
        Collections.sort(ships, (a, b) -> a.getPosition().compareTo(b.getPosition()));
    }

    private boolean processCombatRounds(List<Ship> attackers, List<Ship> defenders, CombatLog log) {
        int round = 1;
        while (!attackers.isEmpty() && !defenders.isEmpty()) {
            log.addRound("Round " + round++);
            processAttackerTurn(attackers, defenders, log);
            if (!defenders.isEmpty()) {
                processDefenderTurn(defenders, attackers, log);
            }
        }
        return !attackers.isEmpty();
    }

    private void processAttackerTurn(List<Ship> attackers, List<Ship> defenders, CombatLog log) {
        for (Ship attacker : attackers) {
            if (defenders.isEmpty()) break;
            Ship target = selectTarget(defenders);
            applyDamage(attacker, target, defenders, log);
        }
    }

    private void processDefenderTurn(List<Ship> defenders, List<Ship> attackers, CombatLog log) {
        for (Ship defender : defenders) {
            if (attackers.isEmpty()) break;
            Ship target = selectTarget(attackers);
            applyDamage(defender, target, attackers, log);
        }
    }

    private Ship selectTarget(List<Ship> ships) {
        return ships.stream()
            .min((a, b) -> Integer.compare(
                a.getShields() + a.getHealth(),
                b.getShields() + b.getHealth()))
            .orElse(ships.get(0));
    }


    // Placeholder for applyDamage method - needs a proper implementation
    private void applyDamage(Ship attacker, Ship target, List<Ship> targetFleet, CombatLog log) {
        int damage = attacker.getAttack();
        if (target.getShields() > 0) {
            int shieldDamage = Math.min(damage, target.getShields());
            target.setShields(target.getShields() - shieldDamage);
            damage -= shieldDamage;
        }
        if (damage > 0) {
            target.setHealth(target.getHealth() - damage);
            if (target.getHealth() <= 0) {
                targetFleet.remove(target);
            }
        }
        log.addEntry(attacker, target, damage);
    }


    private Rewards calculateRewards() {
        return new Rewards(100, 50, 25);
    }
}

class Coordinates3D {
    public int x;
    public int y;
    public int z;

    public Coordinates3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Added compareTo method for sorting
    public int compareTo(Coordinates3D other) {
        if (this.x != other.x) return Integer.compare(this.x, other.x);
        if (this.y != other.y) return Integer.compare(this.y, other.y);
        return Integer.compare(this.z, other.z);
    }
}

class Position {
    private Coordinates3D coordinates;

    public Position(Coordinates3D coordinates) {
        this.coordinates = coordinates;
    }

    public double distanceTo(Position other) {
        int dx = this.coordinates.x - other.coordinates.x;
        int dy = this.coordinates.y - other.coordinates.y;
        int dz = this.coordinates.z - other.coordinates.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    // Added compareTo method for sorting
    public int compareTo(Position other) {
        return this.coordinates.compareTo(other.coordinates);
    }
}


class Fleet {
    private List<Ship> ships;

    public Fleet(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Ship> getShips() {
        return ships;
    }
}

class CombatLog {
    private List<CombatLogEntry> entries;

    public CombatLog() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(Ship attacker, Ship target, int damage) {
        entries.add(new CombatLogEntry(attacker, target, damage));
    }

    public void addRound(String roundInfo) {
        entries.add(new CombatLogEntry(roundInfo));
    }
}


class CombatLogEntry {
    private String roundInfo;
    private Ship attacker;
    private Ship target;
    private int damage;

    public CombatLogEntry(String roundInfo) {
        this.roundInfo = roundInfo;
    }

    public CombatLogEntry(Ship attacker, Ship target, int damage) {
        this.attacker = attacker;
        this.target = target;
        this.damage = damage;
    }

}

class Rewards {
    private int amount;
    private int bonus1;
    private int bonus2;

    public Rewards(int amount, int bonus1, int bonus2) {
        this.amount = amount;
        this.bonus1 = bonus1;
        this.bonus2 = bonus2;
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}

class CombatResult {
    public boolean victory;
    private Rewards rewards;

    public CombatResult(boolean victory, Rewards rewards) {
        this.victory = victory;
        this.rewards = rewards;
    }

    public Rewards getRewards() {
        return rewards;
    }
}

//Separate file: CombatInstance.java
//package main.java;
//public class CombatInstance {}

//Separate file: Ship.java
//package main.java;

//import java.util.HashMap;
//import java.util.Map;

//public class Ship {
//    // ... Ship class implementation ...
//}

//Separate file: ShipModule.java
//package main.java;
//public class ShipModule {
//    // ... ShipModule class implementation ...
//}

//Renamed file: UniverseGenerator.java (was Galaxy.java)
//package main.java;
//public class UniverseGenerator {
//    // ... UniverseGenerator class implementation ...
//}