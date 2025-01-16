
public class EnemyShip extends Ship {
    private String name;
    private String enemyType;
    private int level;
    private boolean isBoss;
    private String[] bossAbilities;
    
    public EnemyShip(int health) {
        super(new Position(new Coordinates3D(0, 0, 0)));
        setHealth(health);
    }

    public EnemyShip(String name, String enemyType, int level, boolean isBoss) {
        super(new Position(new Coordinates3D(0, 0, 0)));
        setHealth(100 * level); // Base health calculation
        this.name = name;
        this.enemyType = enemyType;
        this.level = level;
        this.isBoss = isBoss;
        if(isBoss) {
            initBossAbilities();
        }
    }
    
    private void initBossAbilities() {
        bossAbilities = new String[]{"Shield Overload", "Plasma Storm", "Quantum Torpedo Barrage"};
    }
    
    @Override
    public int calculateDamage() {
        int baseDamage = 50 * level;
        if(isBoss) {
            baseDamage *= 2;
        }
        return baseDamage;
    }
}
