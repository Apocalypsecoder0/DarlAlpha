
public class Inventory {
    private Map<String, List<Equipment>> equipment;
    private Map<String, Integer> resources;
    private int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.equipment = new HashMap<>();
        this.resources = new HashMap<>();
    }

    public boolean addEquipment(Equipment item) {
        if (getTotalItems() >= capacity) return false;
        equipment.computeIfAbsent(item.getType(), k -> new ArrayList<>()).add(item);
        return true;
    }

    public boolean removeEquipment(Equipment item) {
        List<Equipment> items = equipment.get(item.getType());
        return items != null && items.remove(item);
    }

    public int getTotalItems() {
        return equipment.values().stream().mapToInt(List::size).sum();
    }

    public void addResource(String resource, int amount) {
        resources.merge(resource, amount, Integer::sum);
    }

    public boolean useResource(String resource, int amount) {
        int current = resources.getOrDefault(resource, 0);
        if (current >= amount) {
            resources.put(resource, current - amount);
            return true;
        }
        return false;
    }
}
