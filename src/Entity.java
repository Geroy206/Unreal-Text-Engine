
public abstract class Entity {
    protected final String name;
    private final int ID;
    protected float hp;
    protected final Inventory inventory;

    public Entity(String name, int id, float hp) {
        this.name = name;
        this.ID = id;
        this.hp = hp;
        this.inventory = new Inventory(null);
    }

    public String getName() { return name; }

    public int getID() { return ID; }

    public float getHp() { return hp; }

    public void takeDamage(float take) {
        this.hp = Math.max(0, this.hp - take);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public Inventory getInventory() { return inventory; }

    public void getHeal(float toRestore) { this.hp += toRestore; }
}
