
import java.util.*;

public class Fleet {
    private List<Ship> ships;
    private int capacity;

    public Fleet() {
        this.ships = new ArrayList<>();
        this.capacity = 10;
    }

    public boolean addShip(Ship ship) {
        if (ships.size() < capacity) {
            ships.add(ship);
            return true;
        }
        return false;
    }

    public void removeShip(Ship ship) {
        ships.remove(ship);
    }

    public List<Ship> getShips() {
        return new ArrayList<>(ships);
    }
}
