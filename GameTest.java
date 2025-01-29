package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import main.java.*;
import java.util.Set;
import java.util.HashSet;

public class GameTest {
    @Test
    public void testGameInitialization() {
        Game game = new Game();
        assertNotNull("Player should not be null", game.getPlayer());
        assertNotNull("Ship should not be null", game.getShip());
    }

    @Test
    public void testPlayerCreation() {
        Player player = new Player();
        assertEquals("Player should start at level 1", 1, player.getLevel());
        assertNotNull("Fleet should not be null", player.getFleet());
    }

    @Test
    public void testCombatSystem() {
        CombatSystem combat = new CombatSystem();
        Player attacker = new Player();
        Player defender = new Player();

        attacker.getFleet().addShip(new Ship(new Position(new Coordinates3D(0, 0, 0))));
        attacker.getFleet().addShip(new Ship(new Position(new Coordinates3D(0, 0, 0))));
        defender.getFleet().addShip(new Ship(new Position(new Coordinates3D(0, 0, 0))));
        defender.getFleet().addShip(new Ship(new Position(new Coordinates3D(0, 0, 0))));

        CombatResult result = combat.initiateCombat(attacker.getFleet(), defender.getFleet());
        assertNotNull(result);
        assertNotNull(result.getRewards());
        assertTrue(result.getRewards().getClass() == Rewards.class);

        assertEquals(Ship.Position.FRONT,
            attacker.getFleet().getShips().get(0).getPosition());
    }

    @Test
    public void testShipDamageCalculation() {
        Ship testShip = new Ship(new Position(new Coordinates3D(0, 0, 0)));
        CombatSystem combat = new CombatSystem();

        Set<Integer> damages = new HashSet<>();
        for(int i = 0; i < 10; i++) {
            damages.add(combat.calculateDamage(testShip));
        }

        assertTrue(damages.size() > 1);
    }

    @Test
    public void testUniverseGeneration() {
        Universe universe = new Universe();
        assertNotNull(universe);

        Coordinates testCoords = new Coordinates(10, 10);
        Galaxy nearestGalaxy = universe.findGalaxyByCoordinates(testCoords);
        assertNotNull(nearestGalaxy);
    }
}