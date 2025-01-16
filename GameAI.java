
import java.util.List;
import java.util.Random;

public class GameAI {
    private Random random;
    private CombatSystem combatSystem;
    
    public GameAI(CombatSystem combatSystem) {
        this.random = new Random();
        this.combatSystem = combatSystem;
    }
    
    public void processPirateAction(RoguePirate pirate, Fleet playerFleet) {
        // AI decision making for pirates
        if (pirate.getHealth() < pirate.getMaxHealth() * 0.3) {
            // Try to retreat when heavily damaged
            attemptRetreat(pirate);
        } else {
            // Attack aggressively
            Ship target = selectOptimalTarget(playerFleet);
            if (target != null) {
                combatSystem.initiateCombat(createPirateFleet(pirate), playerFleet);
            }
        }
    }
    
    private void attemptRetreat(RoguePirate pirate) {
        // 70% chance to successfully retreat when trying
        if (random.nextDouble() < 0.7) {
            pirate.setPosition(generateRetreatPosition());
        }
    }
    
    private Ship selectOptimalTarget(Fleet playerFleet) {
        List<Ship> ships = playerFleet.getShips();
        if (ships.isEmpty()) return null;
        
        // Target the weakest ship
        return ships.stream()
            .min((a, b) -> Integer.compare(
                a.getHealth() + a.getShields(),
                b.getHealth() + b.getShields()))
            .orElse(ships.get(0));
    }
    
    private Fleet createPirateFleet(RoguePirate pirate) {
        Fleet pirateFleet = new Fleet();
        pirateFleet.addShip(pirate);
        return pirateFleet;
    }
    
    private Position generateRetreatPosition() {
        // Generate a random position for retreat
        return new Position(new Coordinates3D(
            random.nextInt(100),
            random.nextInt(100),
            random.nextInt(100)
        ));
    }
}
