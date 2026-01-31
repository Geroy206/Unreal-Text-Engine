
public abstract class Entity {
    protected final String name;
    private final int ID;
    protected float hp;
    protected float maxHp;
    protected final Inventory inventory;

    public Entity(String name, int id, float hp) {
        this.name = name;
        this.ID = id;
        this.hp = hp;
        this.inventory = new Inventory(null);
        this.maxHp = hp;
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

    public boolean getHeal(float toRestore) {
        System.out.println("\nHP до использование аптечки: " + this.hp);

        if (hp < maxHp) {
            System.out.println(this.getName() + " использовал аптечку!");
            this.hp += toRestore;

            if (hp > maxHp) {
                hp = maxHp;
            }
        } else {
            System.out.println("\nВаше HP максимально. Аптечка сохранена.");
            return false;
        }

        System.out.println("\nHP после использования аптечка: " + this.hp);
        return true;
    }
}
